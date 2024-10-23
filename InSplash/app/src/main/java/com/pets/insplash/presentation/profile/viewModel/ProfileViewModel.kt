package com.pets.insplash.presentation.profile.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.pets.insplash.domain.GetLikedPhotosUseCase
import com.pets.insplash.domain.GetMyProfileUserCase
import com.pets.insplash.domain.SendLikeUseCase
import com.pets.insplash.domain.SendUnlikeUseCase
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.CurrentUserDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.presentation.photosAdapter.pagingSources.LikedPhotosPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val sendLikeUseCase: SendLikeUseCase,
    private val sendUnlikeUseCase: SendUnlikeUseCase,
    private val getLikedPhotosUseCase: GetLikedPhotosUseCase,
    private val getMyProfileUserCase: GetMyProfileUserCase
) : ViewModel() {

    private val _profileFlow = MutableStateFlow<CurrentUserDTO?>(null)
    val profileFlow = _profileFlow.asStateFlow()

    private lateinit var _encryptedSharedPref: SharedPreferences
    private val encryptedSharedPreferences get()= _encryptedSharedPref!!

    var photos: Flow<PagingData<PhotosDTO>> =
        Pager(
            config = PagingConfig(10),
            initialKey = null,
            pagingSourceFactory = {
                LikedPhotosPagingSource(getLikedPhotosUseCase, profileFlow.value?.username ?: "")
            }
        ).flow.cachedIn(viewModelScope)

    fun setSharedPref(context: Context) {
        val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        _encryptedSharedPref = EncryptedSharedPreferences.create(
            Constants.KEY_ENCRYPTED_SHARED_PREF,
            masterKeys,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
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

    fun getProfile() {
        val token = encryptedSharedPreferences.getString(Constants.KEY_TOKEN, "")
        viewModelScope.launch {
            runCatching {
                getMyProfileUserCase.execute(token!!)
            }.fold(
                onFailure = { _profileFlow.value = null },
                onSuccess = { _profileFlow.value = it }
            )
        }
    }

     fun exit(context: Context) {
        clearLocalCache(context)
        clearSharedPref(context)
        clearEncryptedSharedPref()
    }

    private fun clearSharedPref(context: Context){
        val sharedPreferences = context.getSharedPreferences(
            Constants.KEY_APP_SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        ).edit()
        sharedPreferences.clear().apply()
    }

    private fun clearEncryptedSharedPref() {
        encryptedSharedPreferences
            .edit()
            .clear()
            .apply()
    }

    private fun clearLocalCache(context: Context) {
        val cacheName = context.cacheDir.name
        context.deleteFile(cacheName)
    }

}