package com.gdevs.mubi.presentation.popularshow

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdevs.mubi.common.Constants.PAGE_SIZE
import com.gdevs.mubi.common.Resource
import com.gdevs.mubi.data.repository.TvShowRepositoryImplementation
import com.gdevs.mubi.domain.model.TvShowModel
import com.gdevs.mubi.presentation.components.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val repository: TvShowRepositoryImplementation
) : ViewModel() {

    private var currentPage = 1

    var tvShowList = mutableStateOf<List<TvShowModel>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private val newTvShowList = MutableStateFlow<List<TvShowModel>>(listOf())
    val againTvShowList = newTvShowList.asStateFlow()

    init {
        loadTvShowPaginated(category = Category.POPULAR.value)
    }

    fun loadTvShowPaginated(category: String) {
        Log.e("TV SHOW PAGINATED VIEWMODEL", category)
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getTvPopular(currentPage, category)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.totalResults
                    val tvShowEntries = result.data.results.mapIndexed { index, entry ->
                        TvShowModel(
                            id = entry.id,
                            name = entry.name,
                            rate = entry.voteAverage.toString(),
                            date = entry.firstAirDate,
                            poster = entry.posterPath,
                            description = entry.overview
                        )
                    }

                    currentPage += 1
                    loadError.value = ""
                    isLoading.value = false
                    tvShowList.value += tvShowEntries
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun topRated() {
        Log.e("ViewModel", "TOP RATED")
    }

    fun airing() {
        Log.e("ViewModel", "AIRING")
    }

    fun onTv(category: Category?) {
        Log.e("ViewModel", category?.value.toString())
    }

}