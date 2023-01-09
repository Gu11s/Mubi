package com.gdevs.mubi.presentation.popularshow

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.gdevs.mubi.R
import com.gdevs.mubi.domain.model.TvShowModel
import com.gdevs.mubi.presentation.components.Category
import com.gdevs.mubi.presentation.components.ChipGroup
import com.gdevs.mubi.presentation.components.RatingBar
import com.gdevs.mubi.presentation.components.getCategory
import com.gdevs.mubi.presentation.navigation.AppScreens
import com.google.accompanist.coil.CoilImage

@Composable
fun PopularListScreen(
    navController: NavController,
    viewModel: PopularViewModel = hiltNavGraphViewModel()
) {

    val selectedCategory: MutableState<Category?> = mutableStateOf(null)

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Tv Shows")
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "search",
                                tint = Color.White
                            )

                        }
                        IconButton(onClick = {
                            navController.navigate(AppScreens.ProfileScreen.route)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "profile",
                                tint = Color.White
                            )

                        }
                    }
                )

            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ChipGroup(
                    onSelectedChanged = {
                        selectedCategory.value = getCategory(it)
                        viewModel.loadTvShowPaginated(category = it)
                    }
                )

                val categorySelected = selectedCategory.value.toString()
                when (categorySelected) {
                    "RATED" -> PopularList(navController = navController, category = "top_rated")
                    "ONTV" -> PopularList(navController = navController, category = "on_the_air")
                    "AIRING" -> PopularList(
                        navController = navController,
                        category = "airing_today"
                    )
                    else -> PopularList(navController = navController, category = "popular")

                }
            }
        }

    }
}

@Composable
fun PopularList(
    navController: NavController,
    viewModel: PopularViewModel = hiltNavGraphViewModel(),
    category: String
) {
    val tvShowList by remember { viewModel.tvShowList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn( //is recyclerview
        contentPadding = PaddingValues(8.dp)
    ) {
        val itemCount = if (tvShowList.size % 2 == 0) {
            tvShowList.size / 2
        } else {
            tvShowList.size / 2 + 1
        }

        items(itemCount) {
            if (it >= itemCount - 1 && !endReached && !isLoading) {//PAGINATE
                viewModel.loadTvShowPaginated(category = category)
            }
            TvShowRow(rowIndex = it, entries = tvShowList, navController = navController)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadTvShowPaginated(category = category)
            }
        }
    }
}

@Composable
fun TvShowEntry(
    entry: TvShowModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .width(175.dp)
            .height(216.dp)
            .padding(bottom = 8.dp)
            .clickable {
                navController.navigate(
                    AppScreens.DetailScreen.route + "/${entry.id}"
                )
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
    ) {

        Column {
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w220_and_h330_face" + entry.poster)
                    .build(),
                contentDescription = entry.name,
                fadeIn = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(136.dp)
                    .weight(0.6f),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.scale(0.6f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(0.3f),
                verticalArrangement = Arrangement.Center
            ) {
                entry.name?.let { Text(text = it, style = MaterialTheme.typography.body2) }

                Row()
                {
                    RatingBar(rating = entry.rate!!.toFloat(), spaceBetween = 2.dp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = (entry.rate.toFloat() / 2f).toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }

    }
}

@Composable
fun TvShowRow(
    rowIndex: Int,
    entries: List<TvShowModel>,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            TvShowEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                TvShowEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
