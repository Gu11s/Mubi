package com.gdevs.mubi.presentation.season

import androidx.lifecycle.ViewModel
import com.gdevs.mubi.common.Resource
import com.gdevs.mubi.data.remote.dto.season.SeasonDto
import com.gdevs.mubi.data.repository.TvShowRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor(
    private val repository: TvShowRepositoryImplementation
) : ViewModel() {

    suspend fun getSeason(showId: Int?, seasonNumber: Int?): Resource<SeasonDto> {
        return repository.getSeason(showId = showId, seasonNumber = seasonNumber)
    }

}