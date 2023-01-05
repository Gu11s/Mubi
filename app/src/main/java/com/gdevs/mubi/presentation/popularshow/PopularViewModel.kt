package com.gdevs.mubi.presentation.popularshow

import androidx.lifecycle.ViewModel
import com.gdevs.mubi.data.repository.TvShowRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val repository: TvShowRepositoryImplementation
): ViewModel() {


}