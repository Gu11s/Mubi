package com.gdevs.mubi.presentation.detailshow

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.gdevs.mubi.R
import com.gdevs.mubi.common.Resource
import com.gdevs.mubi.data.remote.dto.CreatedBy
import com.gdevs.mubi.data.remote.dto.Season
import com.gdevs.mubi.data.remote.dto.TvShowDetailDto
import com.gdevs.mubi.presentation.popularshow.PopularList
import com.gdevs.mubi.presentation.ui.theme.Primary
import com.gdevs.mubi.presentation.ui.theme.TextColor
import com.google.accompanist.coil.CoilImage
import java.util.*

@Composable
fun DetailScreen(
//    dominantColor: Color,
    showId: Int?,
    navController: NavController,
    topPadding: Dp = 20.dp,
    showImageSize: Dp = 200.dp,
    viewModel: ShowDetailViewModel = hiltNavGraphViewModel()
) {


    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Details(showId = showId, viewModel = viewModel)
        }
    }

}

@Composable
fun Details(
    showId: Int?,
    viewModel: ShowDetailViewModel
) {

    val showInfo = produceState<Resource<TvShowDetailDto>>(initialValue = Resource.Loading()) {
        value = viewModel.getShowInfo(showId)
    }.value

    HeaderSection()

    ShowDetailStateWrapper(showInfo = showInfo)
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .background(
                color = Color.Red
            )
            .fillMaxWidth()
            .height(232.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
//                .clickable {
//                    navController.popBackStack()
//                }
        )

        Text(
            text = "Philipp Lackner",
            fontSize = 24.sp,
            modifier = Modifier.layoutId("username"),
            color = Color.White
        )
    }
}

@Composable
fun ShowDetailStateWrapper(
    showInfo: Resource<TvShowDetailDto>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (showInfo) {
        is Resource.Success -> {
            ShowDetailSection(
                showInfo = showInfo.data!!,
                modifier = modifier
                    .offset(y = (-20).dp)
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
    showInfo: TvShowDetailDto,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
        ShowDetailSeasonSection(seasons = showInfo.seasons)

    }
}

@Composable
fun ShowDetailSeasonSection(
    seasons: List<Season>
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        for (season in seasons) {
            Row {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Row(
//                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
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
                        }
                    }
                }


            }
        }
    }
}


@Composable
fun ShowDetailSeasonData(
    showInfo: TvShowDetailDto,
    seasons: List<Season>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .background(if (showInfo.inProduction) MaterialTheme.colors.primary else MaterialTheme.colors.error)
                .height(35.dp)
        ) {
            Text(
                text = "Latest season: ${seasons.last().name}",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

