package com.example.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.rickandmorty.data.pagingsource.CharacterPagingSource
import com.example.rickandmorty.data.pagingsource.StatusFilterPagingSource
import com.example.rickandmorty.data.entity.CharacterData
import com.example.rickandmorty.data.entity.GenderAction
import com.example.rickandmorty.data.entity.StatusAction
import com.example.rickandmorty.data.pagingsource.GenderFilterPagingSource
import com.example.rickandmorty.data.repo.FavoriteDaoRepository
import com.example.rickandmorty.network.RetroInstance
import com.example.rickandmorty.network.RetroService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersFragmentViewModel @Inject constructor(var retroService: RetroService) :ViewModel() {

    fun getCharacterList(): Flow<PagingData<CharacterData>> {
        return Pager (config = PagingConfig(pageSize = 42, maxSize = 826),
            pagingSourceFactory = { CharacterPagingSource(retroService) }).flow.cachedIn(viewModelScope)
    }

    fun getStatusCharacter(statusAction: StatusAction): Flow<PagingData<CharacterData>> {
        return Pager (config = PagingConfig(pageSize = 22, maxSize = 439),
            pagingSourceFactory = { StatusFilterPagingSource(retroService,statusAction) }).flow.cachedIn(viewModelScope)
    }

    fun getGenderCharacter(genderAction: GenderAction): Flow<PagingData<CharacterData>> {
        return Pager (config = PagingConfig(pageSize = 42, maxSize = 826),
            pagingSourceFactory = { GenderFilterPagingSource(retroService,genderAction) }).flow.cachedIn(viewModelScope)
    }
}