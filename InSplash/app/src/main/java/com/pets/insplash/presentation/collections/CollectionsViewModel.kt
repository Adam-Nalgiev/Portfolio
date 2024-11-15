package com.pets.insplash.presentation.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pets.insplash.domain.GetCollectionsUseCase
import com.pets.insplash.entity.dto.CollectionDTO
import com.pets.insplash.presentation.collections.adapter.CollectionsAdapterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {

    var photos: Flow<PagingData<CollectionDTO>> =
        Pager(
            config = PagingConfig(10),
            initialKey = null,
            pagingSourceFactory = { CollectionsAdapterPagingSource(getCollectionsUseCase) }
        ).flow.cachedIn(viewModelScope)

}