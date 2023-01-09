package com.gdevs.mubi.presentation.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.gdevs.mubi.R
import com.gdevs.mubi.presentation.components.RatingBar
import com.gdevs.mubi.presentation.navigation.AppScreens
import com.google.accompanist.coil.CoilImage
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.gdevs.mubi.presentation.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Profile")
                    },
                    navigationIcon =
                    {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProfileInfo()
                }

                Column {
                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "My Favorites",
                        style = MaterialTheme.typography.h6,
                        color = TextColor
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MyFavoritesShows()
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                Logout()

            }

        }
    }
}

@Composable
fun ProfileInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "profile"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 75.dp, start = 50.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(shape = CircleShape)
                        .background(color = Primary),
                    painter = painterResource(R.drawable.ic_baseline_edit_24),
                    contentDescription = "edit"
                )
            }
        }

        Text(
            text = "Gustavo Garcia Salas",
            style = MaterialTheme.typography.subtitle1,
            color = TextColor
        )
        Text(
            text = "@Gu11s",
            style = MaterialTheme.typography.caption,
            color = SubtleText
        )
    }
}

@Composable
fun MyFavoritesShows() {
    Card(
        modifier = Modifier
            .width(175.dp)
            .height(216.dp)
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
    ) {

        Column {
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w220_and_h330_face/zI3E2a3WYma5w8emI35mgq5Iurx.jpg")
                    .build(),
                contentDescription = "sipson",
                fadeIn = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(136.dp)
                    .weight(0.6f),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.scale(0.6f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(0.3f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "SHOW NAME", style = MaterialTheme.typography.body2)

                Row()
                {
                    RatingBar(rating = 5f, spaceBetween = 2.dp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = (5).toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Composable
fun Logout() {

    var show by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Primary),
            onClick = { show = true },
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "LOG OUT",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }

    }

    Dialog(show, { show = false }, {})
}

@Composable
fun Dialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            shape = RoundedCornerShape(8.dp),
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(
                        text = "STAY",
                        style = MaterialTheme.typography.button,
                        color = Primary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(
                        text = "LEAVE",
                        style = MaterialTheme.typography.button,
                        color = Error
                    )
                }
            },
            text = { Text(text = "Are you sure you want to leave?") }

        )
    }
}