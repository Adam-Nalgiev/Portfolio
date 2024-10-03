package com.pets.insplash.presentation.home.viewModel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pets.insplash.domain.GetHomePhotosUseCase
import com.pets.insplash.domain.GetRandomPhotoUseCase
import com.pets.insplash.domain.SendLikeUseCase
import com.pets.insplash.domain.SendUnlikeUseCase
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.presentation.home.adapter.HomeAdapterPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val sendUnlikeUseCase: SendUnlikeUseCase,
    private val sendLikeUseCase: SendLikeUseCase,
    private val getRandomPhotoUseCase: GetRandomPhotoUseCase,
    private val getHomePhotosUseCase: GetHomePhotosUseCase,
) : ViewModel() {

    var pagedPhotos: Flow<PagingData<PhotosDTO>> =
        Pager(
            config = PagingConfig(20),
            initialKey = null,
            pagingSourceFactory = { HomeAdapterPagingSource(getHomePhotosUseCase) }
        ).flow.cachedIn(viewModelScope)

    fun authState(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(
            Constants.KEY_APP_SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(Constants.KEY_IS_AUTHORIZED, false)
    }

    fun getPhoto(): OnePhotoDTO? {
        var photo: OnePhotoDTO? = null
        viewModelScope.launch(Dispatchers.IO) {
            photo = getRandomPhotoUseCase.execute()
        }
        return photo
    }

    fun sendUnlike(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendUnlikeUseCase.execute(id)
        }
    }

    fun sendLike(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendLikeUseCase.execute(id)
        }
    }

    fun changeRecyclerData() {

    }
}