package com.gdevs.mubi.presentation.detailshow

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.gdevs.mubi.R
import com.gdevs.mubi.common.Resource
import com.gdevs.mubi.data.remote.dto.Season
import com.gdevs.mubi.data.remote.dto.TvShowDetailDto
import com.gdevs.mubi.presentation.components.RatingBar
import com.gdevs.mubi.presentation.navigation.AppScreens
import com.gdevs.mubi.presentation.ui.theme.Primary
import com.gdevs.mubi.presentation.ui.theme.TextColor
import com.gdevs.mubi.presentation.ui.theme.WhiteText
import com.google.accompanist.coil.CoilImage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailScreen(
    showId: Int?,
    navController: NavController,
    viewModel: ShowDetailViewModel = hiltNavGraphViewModel()
) {

    val lazyState = rememberLazyListState()
    val visibleItem by remember {
        derivedStateOf { lazyState.firstVisibleItemIndex >= 1 }
    }

    val showInfo = produceState<Resource<TvShowDetailDto>>(initialValue = Resource.Loading()) {
        value = viewModel.getShowInfo(showId)
    }.value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            topBar = {
                AnimatedVisibility(
                    visible = visibleItem,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "back"
                                )
                            }
                        },
                        title = {
                            Text(showInfo.data!!.name)
                        }
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Details(showId = showId, navController = navController, showInfo = showInfo)
            }
        }
    }

}

@Composable
fun Details(
    showId: Int?,
    navController: NavController,
    showInfo: Resource<TvShowDetailDto>
) {


    ShowDetailStateWrapper(showId = showId, navController = navController, showInfo = showInfo)
}


@Composable
fun ShowDetailStateWrapper(
    showId: Int?,
    navController: NavController,
    showInfo: Resource<TvShowDetailDto>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (showInfo) {
        is Resource.Success -> {
            ShowDetailSection(
                showId = showId,
                navController = navController,
                showInfo = showInfo.data!!
            )
        }
        is Resource.Error -> {
            Text(
                text = showInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun ShowDetailSection(
    showId: Int?,
    navController: NavController,
    showInfo: TvShowDetailDto,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomStart
            ) {
                CoilImage(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w220_and_h330_face" + showInfo.posterPath)
                        .build(),
                    contentDescription = showInfo.name,
                    fadeIn = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(232.dp),
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.Crop
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.scale(0.6f)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = showInfo.originalName,
                        fontWeight = FontWeight.Bold,
                        color = WhiteText,
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = showInfo.name,
                        fontWeight = FontWeight.Bold,
                        color = WhiteText,
                        style = MaterialTheme.typography.h4,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        RatingBar(rating = showInfo.voteAverage.toFloat(), spaceBetween = 2.dp)
                    }

                }
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = "Summary",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = Primary,
                fontStyle = MaterialTheme.typography.h6.fontStyle
            )

            Text(
                text = showInfo.overview,
                textAlign = TextAlign.Start,
                color = TextColor,
                fontStyle = MaterialTheme.typography.body2.fontStyle
            )
            ShowDetailSeasonSection(
                seasons = showInfo.seasons,
                showId = showId,
                navController = navController
            )
        }
    }
}

@Composable
fun ShowDetailSeasonSection(
    showId: Int?,
    seasons: List<Season>,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        for (season in seasons) {
            Row {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            navController.navigate(
                                AppScreens.SeasonScreen.route + "/${showId}/${season.seasonNumber}"
                            )
                        }
                ) {
                    Row {
                        CoilImage(
                            request = ImageRequest.Builder(LocalContext.current)
                                .data("https://image.tmdb.org/t/p/w220_and_h330_face" + season.posterPath)
                                .build(),
                            contentDescription = season.name,
                            fadeIn = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(147.dp)
                                .weight(0.3f),
                            alignment = Alignment.CenterStart,
                            contentScale = ContentScale.Crop
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.scale(0.6f)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(0.7f)
                                .padding(start = 8.dp)
                        ) {

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = season.name,
                                color = TextColor,
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Normal
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${season.episodeCount.toString()} Episodes",
                                color = Primary,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = season.overview,
                                color = TextColor,
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.body2
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }


            }
        }
    }
}

