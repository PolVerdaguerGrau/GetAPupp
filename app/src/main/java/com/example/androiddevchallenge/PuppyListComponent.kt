package com.example.androiddevchallenge

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ChoosePuppy(navController: NavController, puppyListViewModel: PuppyListViewModel) {
    Column {
        Text(
            style = MaterialTheme.typography.h6,
            text = "Choose your favourite!",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp)
        )
        LazyColumn(modifier = Modifier.padding(5.dp)) {

            items(items = puppyListViewModel.getPuppies()) { puppy ->
                PuppyItem(puppy = puppy, navController, puppyListViewModel)
            }
        }
    }
}

@Composable
fun PuppyItem(puppy: String, navController: NavController, puppyListViewModel: PuppyListViewModel) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(70.dp)
                .clickable {
                    navController.navigate("puppyPage/$puppy")
                }
        ) {
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .padding(20.dp, 0.dp)
                    .width(50.dp)
                    .clip(CircleShape),
            ) {
                val infiniteTransition = rememberInfiniteTransition()
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = 200
                            0.7f at 100
                        },
                        repeatMode = RepeatMode.Reverse
                    )
                )
                puppyListViewModel.getPuppyImage(puppy).observeAsState().value?.let {
                    CoilImage(
                        data = it, contentDescription = "A  dog",
                        contentScale = ContentScale.Crop,

                        loading = {
                            Spacer(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red.copy(alpha = alpha))
                            )
                        }
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clip(CircleShape)
                            .background(Color.Red.copy(alpha = alpha))
                    )
                }
            }
            Text(
                style = MaterialTheme.typography.h6, text = puppy, modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Divider(color = Color.White)
    }
}