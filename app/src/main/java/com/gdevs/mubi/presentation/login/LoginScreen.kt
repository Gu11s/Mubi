package com.gdevs.mubi.presentation.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.gdevs.mubi.R
import com.gdevs.mubi.common.LoadingState
import com.gdevs.mubi.presentation.navigation.AppScreens
import com.gdevs.mubi.presentation.ui.theme.Background
import com.gdevs.mubi.presentation.ui.theme.Primary
import com.gdevs.mubi.presentation.ui.theme.TextColor

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    context: Context
) {

    val state by viewModel.loadingState.collectAsState()

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
                    viewModel.signInWithEmailAndPassword("user@mubi.com", "MubiTest123")
//                    navController.popBackStack()
//                    navController.navigate(AppScreens.PopularListScreen.route)
                }) {
                Text(
                    text = "LOG IN",
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )

            }

            when(state.status) {
                LoadingState.Status.SUCCESS -> {
                    Toast.makeText(context, "Welcome", Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                    navController.navigate(AppScreens.PopularListScreen.route)
                }
                LoadingState.Status.FAILED -> {
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }

        }

    }

}