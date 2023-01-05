package com.gdevs.mubi.presentation.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen: AppScreens("splash")
    object PopularShowsScreen: AppScreens("popular")
}