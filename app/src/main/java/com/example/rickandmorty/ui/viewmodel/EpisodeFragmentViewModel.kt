package com.example.rickandmorty.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.entity.*
import com.example.rickandmorty.data.pagingsource.EpisodePagingSource
import com.example.rickandmorty.data.pagingsource.GenderFilterPagingSource
import com.example.rickandmorty.data.pagingsource.SeasonFilterPagingSource
import com.example.rickandmorty.network.RetroInstance
import com.example.rickandmorty.network.RetroService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodeFragmentViewModel @Inject constructor(var retroService: RetroService) : ViewModel() {
    var episodeList = MutableLiveData<EpisodeList>()


    fun getEpisodeList(): Flow<PagingData<EpisodeData>> {
        return Pager (config = PagingConfig(pageSize =  4, maxSize = 51),
            pagingSourceFactory = { EpisodePagingSource(retroService) }).flow.cachedIn(viewModelScope)
    }

    fun getGenderCharacter(seasonAction: SeasonAction): Flow<PagingData<EpisodeData>> {
        return Pager (config = PagingConfig(pageSize = 42, maxSize = 826),
            pagingSourceFactory = { SeasonFilterPagingSource(retroService,seasonAction) }).flow.cachedIn(viewModelScope)
    }
}