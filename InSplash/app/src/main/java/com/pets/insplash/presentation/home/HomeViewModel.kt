package com.pets.insplash.presentation.home

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pets.insplash.databinding.FragmentHomeBinding
import com.pets.insplash.domain.GetFoundPhotosUseCase
import com.pets.insplash.domain.GetHomePhotosUseCase
import com.pets.insplash.domain.GetRandomPhotoUseCase
import com.pets.insplash.domain.SendLikeUseCase
import com.pets.insplash.domain.SendUnlikeUseCase
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.presentationModels.ImageDataModel
import com.pets.insplash.presentation.photosAdapter.pagingSources.PhotosPagingSource
import com.pets.insplash.presentation.photosAdapter.pagingSources.FoundPhotosPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sendUnlikeUseCase: SendUnlikeUseCase,
    private val sendLikeUseCase: SendLikeUseCase,
    private val getRandomPhotoUseCase: GetRandomPhotoUseCase,
    private val getHomePhotosUseCase: GetHomePhotosUseCase,
    private val getFoundPhotosUseCase: GetFoundPhotosUseCase,
) : ViewModel() {

    private val _newPhotoFlow = MutableStateFlow<ImageDataModel?>(null)
    val newPhotoFlow = _newPhotoFlow.asStateFlow()

    var photos: Flow<PagingData<PhotosDTO>> =
        Pager(
            config = PagingConfig(10),
            initialKey = null,
            pagingSourceFactory = { PhotosPagingSource(getHomePhotosUseCase) }
        ).flow.cachedIn(viewModelScope)

    init {
        setNewPhoto()
    }

    fun authState(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(
            Constants.KEY_APP_SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(Constants.KEY_IS_AUTHORIZED, false)
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

    fun changeRecyclerData(requestText: String?) {
        photos = if (requestText == null) {
            Pager(
                config = PagingConfig(30),
                initialKey = null,
                pagingSourceFactory = { PhotosPagingSource(getHomePhotosUseCase) }
            ).flow.cachedIn(viewModelScope)
        } else {
            Pager(
                config = PagingConfig(30),
                initialKey = null,
                pagingSourceFactory = { FoundPhotosPagingSource(getFoundPhotosUseCase, requestText) }
            ).flow.cachedIn(viewModelScope)
        }
    }

    private fun setNewPhoto(dto: OnePhotoDTO? = null) {
        viewModelScope.launch {
            if (dto == null)
                _newPhotoFlow.value = imageDataMapper()
            else
                _newPhotoFlow.value = imageDataMapper(dto)
        }
    }

    private suspend fun imageDataMapper(dto: OnePhotoDTO? = null): ImageDataModel? {
        return if (dto != null) {
            ImageDataModel(
                id = dto.id,
                imageUrl = dto.urls.regular ?: "",
                profileImageUrl = dto.user.profile_image?.medium ?: "",
                username = dto.user.name,
                userLogin = dto.user.username,
                likesCount = dto.likes,
                isLikedByUser = dto.liked_by_user ?: false
            )
        } else {
            val photo = getPhoto()
            return if (photo != null) {
                ImageDataModel(
                    id = photo.id,
                    imageUrl = photo.urls.regular ?: "",
                    profileImageUrl = photo.user.profile_image?.medium ?: "",
                    username = photo.user.name,
                    userLogin = photo.user.username,
                    likesCount = photo.likes,
                    isLikedByUser = photo.liked_by_user ?: false
                )
            } else {
                return null
            }
        }
    }

    private suspend fun getPhoto(): OnePhotoDTO? {
        return viewModelScope.async(Dispatchers.IO) { getRandomPhotoUseCase.execute() }.await()
    }

    fun hideImage(binding: FragmentHomeBinding) {
        binding.progressCircular.visibility = View.GONE
        binding.imageInteresting.visibility = View.GONE
        binding.textNew.visibility = View.GONE
        binding.textInterestingPhoto.visibility = View.GONE
        binding.viewLikes.visibility = View.GONE
        binding.viewUserProfile.visibility = View.GONE
    }

    fun setImageData(imageData: ImageDataModel, binding: FragmentHomeBinding) {
        val id = imageData.id
        val isLiked = imageData.isLikedByUser
        val profileImageUrl = imageData.profileImageUrl!!
        val username = imageData.username
        val login = imageData.userLogin
        val likesCount = imageData.likesCount

        with(binding.viewUserProfile) {
            setImage(profileImageUrl)
            setUsername(username)
            setUserLogin(login)
        }

        with(binding.viewLikes) {
            setLikesCount(likesCount)
            setLikesState(isLiked, id)
        }
    }
}