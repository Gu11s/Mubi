package com.gdevs.mubi.presentation.detailshow

import androidx.lifecycle.ViewModel
import com.gdevs.mubi.common.Resource
import com.gdevs.mubi.data.remote.dto.TvShowDetailDto
import com.gdevs.mubi.data.repository.TvShowRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val repository: TvShowRepositoryImplementation
) : ViewModel() {

    suspend fun getShowInfo(showId: Int?): Resource<TvShowDetailDto> {
        return repository.getShowInfo(showId = showId)
    }

}
