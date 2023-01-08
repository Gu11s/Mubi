package com.gdevs.mubi.presentation.detailshow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gdevs.mubi.common.Resource
import com.gdevs.mubi.data.remote.dto.TvShowDetailDto
import com.gdevs.mubi.data.repository.TvShowRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val repository: TvShowRepositoryImplementation
) : ViewModel() {

    suspend fun getShowInfo(showId: Int?): Resource<TvShowDetailDto> {
        Log.d("TV RESULT ID", showId.toString())
        val response = repository.getShowInfo(showId = showId)
//        insert(response.data!!)
        return response
    }

//    fun insert(show: TvShowDetailDto) = viewModelScope.launch {
//        repository.insert(show)
//    }

}

class WordViewModelFactory(private val repository: TvShowRepositoryImplementation) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShowDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
