package com.pets.insplash.presentation.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pets.insplash.domain.GetRandomPhotoUseCase
import com.pets.insplash.domain.SendLikeUseCase
import com.pets.insplash.domain.SendUnlikeUseCase
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {

    private var requestText = ""

    var pagedPhotos: Flow<PagingData<PhotosDTO>> =
        Pager(config = PagingConfig(20), null, pagingSourceFactory = { HomeAdapterPagingSource(requestText) }).flow.cachedIn(
            viewModelScope
        )
    var pagedFoundPhotos: Flow<PagingData<PhotosDTO>> =
        Pager(config = PagingConfig(20), null, pagingSourceFactory = { HomeAdapterPagingSource(requestText) }).flow.cachedIn(
            viewModelScope
        )

    fun authState(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(
            Constants.KEY_APP_SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(Constants.KEY_IS_AUTHORIZED, false)
    }

    fun setRequestText(searchTerms: String) {
        requestText = searchTerms
    }

    suspend fun getPhoto(): OnePhotoDTO {
        return GetRandomPhotoUseCase().execute()
    }

    suspend fun sendUnlike(id: String) {
        SendUnlikeUseCase().execute(id)
    }

    suspend fun sendLike(id: String) {
        SendLikeUseCase().execute(id)
    }
}