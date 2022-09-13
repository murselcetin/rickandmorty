package com.example.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.pagingsource.LocationPagingSource
import com.example.rickandmorty.data.entity.LocationData
import com.example.rickandmorty.network.RetroInstance
import com.example.rickandmorty.network.RetroService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LocationFragmentViewModel @Inject constructor(var retroService: RetroService): ViewModel() {

    fun getLocationList(): Flow<PagingData<LocationData>> {
        return Pager (config = PagingConfig(pageSize = 7, maxSize = 126),
            pagingSourceFactory = { LocationPagingSource(retroService) }).flow.cachedIn(viewModelScope)
    }
}