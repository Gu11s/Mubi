package com.gdevs.mubi.presentation.season

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.gdevs.mubi.presentation.detailshow.Details
import com.gdevs.mubi.presentation.detailshow.ShowDetailViewModel

@Composable
fun SeasonScreen(
    showId: Int?,
    seasonNumber: Int?,
    navController: NavController,
    topPadding: Dp = 20.dp,
    showImageSize: Dp = 200.dp,
    viewModel: SeasonViewModel = hiltNavGraphViewModel()
){

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = showId.toString()
            )
        }
    }

}