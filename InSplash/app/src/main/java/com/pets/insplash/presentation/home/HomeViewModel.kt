package com.pets.insplash.presentation.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.PhotosDTO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class HomeViewModel : ViewModel() {

    private val _isSearching = Channel<Boolean>{}
    val isSearching = _isSearching.receiveAsFlow()

    var pagedPhotos: Flow<PagingData<PhotosDTO>> =
        Pager(config = PagingConfig(20), null, pagingSourceFactory = { HomeAdapterPagingSource() }).flow.cachedIn(
            viewModelScope
        )
    var pagedFoundPhotos: Flow<PagingData<PhotosDTO>> =
        Pager(config = PagingConfig(20), null, pagingSourceFactory = { HomeAdapterPagingSource() }).flow.cachedIn(
            viewModelScope
        )

    fun authState(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(Constants.KEY_APP_SHARED_PREF, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getBoolean(Constants.KEY_IS_AUTHORIZED, false)
    }

    suspend fun updateSearchingState(state: Boolean) {
        _isSearching.send(state)
    }
}