package com.gdevs.mubi.presentation.popularshow

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdevs.mubi.common.Constants.PAGE_SIZE
import com.gdevs.mubi.common.Resource
import com.gdevs.mubi.data.remote.dto.Result
import com.gdevs.mubi.data.repository.TvShowRepositoryImplementation
import com.gdevs.mubi.domain.model.TvShowModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val repository: TvShowRepositoryImplementation
): ViewModel() {

    private var currentPage = 1

    var tvShowList = mutableStateOf<List<TvShowModel>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadTvShowPaginated()
    }

    fun loadTvShowPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getTvPopular(currentPage)
            insertShow(result.data!!.results)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data.totalResults
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


    private fun insertShow(list: List<Result>) = viewModelScope.launch{
        repository.insert(*list.toTypedArray())
    }

}