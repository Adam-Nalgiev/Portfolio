package com.pets.insplash.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pets.insplash.domain.ClearLocalCacheUseCase
import com.pets.insplash.domain.ClearSharedPrefUseCase
import com.pets.insplash.domain.GetLikedPhotosUseCase
import com.pets.insplash.domain.GetMyProfileUseCase
import com.pets.insplash.domain.SendLikeUseCase
import com.pets.insplash.domain.SendUnlikeUseCase
import com.pets.insplash.entity.dto.CurrentUserDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.presentation.photosAdapter.pagingSources.LikedPhotosPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sendLikeUseCase: SendLikeUseCase,
    private val sendUnlikeUseCase: SendUnlikeUseCase,
    private val getLikedPhotosUseCase: GetLikedPhotosUseCase,
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val clearSharedPrefUseCase: ClearSharedPrefUseCase,
    private val clearLocalCacheUseCase: ClearLocalCacheUseCase
) : ViewModel() {

    private val _profileFlow = MutableStateFlow<CurrentUserDTO?>(null)
    val profileFlow = _profileFlow.asStateFlow()

    init {
        getProfile()
    }

    var photos: Flow<PagingData<PhotosDTO>> =
        Pager(
            config = PagingConfig(10),
            initialKey = null,
            pagingSourceFactory = {
                LikedPhotosPagingSource(getLikedPhotosUseCase, profileFlow.value?.username ?: "")
            }
        ).flow.cachedIn(viewModelScope)

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

    fun exit() {
        clearLocalCacheUseCase.execute()
        clearSharedPrefUseCase.execute()
    }

    private fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getMyProfileUseCase.execute()
            }.fold(
                onFailure = { _profileFlow.value = null },
                onSuccess = { _profileFlow.value = it }
            )
        }
    }

}