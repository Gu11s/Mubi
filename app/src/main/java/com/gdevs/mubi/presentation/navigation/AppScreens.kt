package com.gdevs.mubi.presentation.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen: AppScreens("splash")
    object PopularListScreen: AppScreens("popular")
    object DetailScreen: AppScreens("detail")
    object SeasonScreen: AppScreens("season")
}