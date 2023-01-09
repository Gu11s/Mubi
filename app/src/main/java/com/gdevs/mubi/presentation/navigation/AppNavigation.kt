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
import com.gdevs.mubi.presentation.profile.ProfileScreen
import com.gdevs.mubi.presentation.season.SeasonScreen

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
        composable(AppScreens.PopularListScreen.route) {
            PopularListScreen(navController)
        }
        composable(AppScreens.DetailScreen.route + "/{showId}",
            arguments = listOf(
                navArgument("showId") {
                    type = NavType.IntType
                }
            )
        ) {
            val showId = remember {
                it.arguments?.getInt("showId")
            }
            DetailScreen(
                showId = showId,
                navController = navController
            )
        }
        composable(AppScreens.SeasonScreen.route + "/{showId}/{seasonNumber}",
            arguments = listOf(
                navArgument("showId") {
                    type = NavType.IntType
                },
                navArgument("seasonNumber") {
                    type = NavType.IntType
                }
            )
        ) {
            val showId = remember {
                it.arguments?.getInt("showId")
            }
            val seasonNumber = remember {
                it.arguments?.getInt("seasonNumber")
            }
            SeasonScreen(
                showId = showId,
                seasonNumber = seasonNumber,
                navController = navController
            )
        }
        composable(AppScreens.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}