package com.gdevs.mubi.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.gdevs.mubi.R
import com.gdevs.mubi.presentation.navigation.AppScreens
import com.gdevs.mubi.presentation.ui.theme.Background
import com.gdevs.mubi.presentation.ui.theme.Primary
import com.gdevs.mubi.presentation.ui.theme.TextColor

@Composable
fun LoginScreen(
    navController: NavController
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .size(121.dp, 144.dp),
                painter = painterResource(id = R.drawable.logo_login),
                contentDescription = "login"
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Sing in to your account",
                style = MaterialTheme.typography.body1,
                color = TextColor
            )

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = Primary),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.PopularListScreen.route)
                }) {
                Text(
                    text = "LOG IN",
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )

            }

        }

    }

}