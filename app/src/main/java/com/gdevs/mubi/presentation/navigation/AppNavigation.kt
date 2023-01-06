package com.gdevs.mubi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.gdevs.mubi.presentation.SplashScreen
import com.gdevs.mubi.presentation.detailshow.DetailScreen
import com.gdevs.mubi.presentation.popularshow.PopularListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route
    ) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(AppScreens.PopularListScreen.route){
            PopularListScreen(navController)
        }
       composable(AppScreens.DetailScreen.route){
           DetailScreen()
       }
    }
}