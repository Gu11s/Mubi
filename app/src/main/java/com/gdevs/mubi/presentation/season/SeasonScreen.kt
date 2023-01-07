package com.gdevs.mubi.presentation.season

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
import com.gdevs.mubi.data.remote.dto.season.Episode
import com.gdevs.mubi.data.remote.dto.season.SeasonDto
import com.gdevs.mubi.presentation.detailshow.*
import com.gdevs.mubi.presentation.navigation.AppScreens
import com.gdevs.mubi.presentation.ui.theme.Primary
import com.gdevs.mubi.presentation.ui.theme.TextColor
import com.google.accompanist.coil.CoilImage
import java.util.*

@Composable
fun SeasonScreen(
    showId: Int?,
    seasonNumber: Int?,
    navController: NavController,
    viewModel: SeasonViewModel = hiltNavGraphViewModel()
) {

    val seasonInfo = produceState<Resource<SeasonDto>>(initialValue = Resource.Loading()) {
        value = viewModel.getSeason(showId, seasonNumber)
    }.value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            topBar = {
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
                        Text("Season $seasonNumber")
                    }
                )

            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SeasonDetailStateWrapper(
                    seasonInfo = seasonInfo
                )
            }
        }
    }

}


@Composable
fun SeasonDetailStateWrapper(
    seasonInfo: Resource<SeasonDto>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (seasonInfo) {
        is Resource.Success -> {
            SeasonDetailSection(
                seasonInfo = seasonInfo.data!!
            )
        }
        is Resource.Error -> {
            Text(
                text = seasonInfo.message!!,
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
fun SeasonDetailSection(
    seasonInfo: SeasonDto,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {

        SeasonDetailEpisodesSection(episodes = seasonInfo.episodes)

    }
}

@Composable
fun SeasonDetailEpisodesSection(
    episodes: List<Episode>,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        for (episode in episodes) {
            Row {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 1.dp,
                ) {
                    Row {
                        CoilImage(
                            request = ImageRequest.Builder(LocalContext.current)
                                .data("https://image.tmdb.org/t/p/w220_and_h330_face" + episode.stillPath)
                                .build(),
                            contentDescription = episode.name,
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
                                text = episode.name,
                                color = TextColor,
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.h6
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = episode.overview,
                                color = TextColor,
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.body2
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${episode.runtime.toString()} m",
                                color = Primary,
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
