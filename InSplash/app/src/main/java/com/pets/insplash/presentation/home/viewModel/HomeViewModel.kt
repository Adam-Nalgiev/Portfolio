package com.pets.insplash.presentation.home.viewModel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pets.insplash.domain.GetFoundPhotosUseCase
import com.pets.insplash.domain.GetHomePhotosUseCase
import com.pets.insplash.domain.GetRandomPhotoUseCase
import com.pets.insplash.domain.SendLikeUseCase
import com.pets.insplash.domain.SendUnlikeUseCase
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.presentationModels.ImageDataModel
import com.pets.insplash.presentation.home.adapter.HomeAdapterPagingSource
import com.pets.insplash.presentation.home.adapter.HomeAdapterSearchPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val sendUnlikeUseCase: SendUnlikeUseCase,
    private val sendLikeUseCase: SendLikeUseCase,
    private val getRandomPhotoUseCase: GetRandomPhotoUseCase,
    private val getHomePhotosUseCase: GetHomePhotosUseCase,
    private val getFoundPhotosUseCase: GetFoundPhotosUseCase,
) : ViewModel() {

    var photos: Flow<PagingData<PhotosDTO>> =
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

    fun changeRecyclerData(requestText: String?) {
        photos = if (requestText == null) {
            Pager(
                config = PagingConfig(20),
                initialKey = null,
                pagingSourceFactory = { HomeAdapterPagingSource(getHomePhotosUseCase) }
            ).flow.cachedIn(viewModelScope)
        } else {
            Pager(
                config = PagingConfig(20),
                initialKey = null,
                pagingSourceFactory = { HomeAdapterSearchPagingSource(getFoundPhotosUseCase, requestText) }
            ).flow.cachedIn(viewModelScope)
        }
    }

    suspend fun imageDataMapper(dto: OnePhotoDTO? = null): ImageDataModel? {
        return if (dto != null) {
            ImageDataModel(
                id = dto.id,
                imageUrl = dto.urls.full ?: "",
                profileImageUrl = dto.user.profile_image?.medium ?: "",
                username = dto.user.name,
                userLogin = dto.user.username,
                likesCount = dto.likes,
                isLikedByUser = dto.liked_by_user
            )
        } else {
            val photo = getPhoto()
            return ImageDataModel(
                id = photo.id,
                imageUrl = photo.urls.full ?: "",
                profileImageUrl = photo.user.profile_image?.medium ?: "",
                username = photo.user.name,
                userLogin = photo.user.username,
                likesCount = photo.likes,
                isLikedByUser = photo.liked_by_user
            )
        }
    }

    private suspend fun getPhoto(): OnePhotoDTO {
        return viewModelScope.async(Dispatchers.IO) { getRandomPhotoUseCase.execute() }.await()
    }
}