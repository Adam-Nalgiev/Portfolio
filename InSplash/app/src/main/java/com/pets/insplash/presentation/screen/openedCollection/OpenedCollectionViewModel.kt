package com.pets.insplash.presentation.screen.openedCollection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pets.insplash.domain.GetCollectionsPhotosUseCase
import com.pets.insplash.domain.SendLikeUseCase
import com.pets.insplash.domain.SendUnlikeUseCase
import com.pets.insplash.entity.Photos
import com.pets.insplash.presentation.photosAdapter.pagingSources.OpenedCollectionPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenedCollectionViewModel @Inject constructor(
    private val getCollectionsPhotosUseCase: GetCollectionsPhotosUseCase,
    private val sendUnlikeUseCase: SendUnlikeUseCase,
    private val sendLikeUseCase: SendLikeUseCase
) : ViewModel() {

    private var id = ""

    var photos: Flow<PagingData<Photos>> =
        Pager(
            config = PagingConfig(10),
            initialKey = null,
            pagingSourceFactory = { OpenedCollectionPagingSource(getCollectionsPhotosUseCase, id) }
        ).flow.cachedIn(viewModelScope)

    fun setId(value: String) {
        id = value
    }

    fun sendUnlike(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("UNLIKE", "UNLIKE SEND VM")
            sendUnlikeUseCase.execute(id)
        }
    }

    fun sendLike(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("LIKE", "LIKE SEND VM")
            sendLikeUseCase.execute(id)
        }
    }
}