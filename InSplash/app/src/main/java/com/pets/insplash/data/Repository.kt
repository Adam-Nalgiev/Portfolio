package com.pets.insplash.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.pets.insplash.data.network.NetworkClient
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.CollectionDTO
import com.pets.insplash.entity.dto.CurrentUserDTO
import com.pets.insplash.entity.dto.LikedPhotosDTO
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.dto.TokenBodyDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val client: NetworkClient,
    @ApplicationContext private val context: Context
) {

    suspend fun getMyProfile(): CurrentUserDTO? {
        val token = getEncryptedSharedPref().getString(Constants.KEY_TOKEN, "")
        return client.request.getProfile("Bearer $token")
    }

    suspend fun getCollections(page: Int): List<CollectionDTO>? {
        return client.request.getCollections(page = page)
    }

    suspend fun getCollectionsPhotos(id: String, page: Int): List<PhotosDTO>? {
        return client.request.getCollectionPhotos(id = id, page = page)
    }

    suspend fun sendLike(photoId: String) {
        client.request.likePhoto(id = photoId)
    }

    suspend fun sendUnlike(photoId: String) {
        client.request.unlikePhoto(id = photoId)
    }

    suspend fun getPhoto(id: String): OnePhotoDTO? {
        return client.request.getOnePhoto(id = id)
    }

    suspend fun getRandomPhoto(): OnePhotoDTO? {
        return client.request.getRandomPhoto()
    }

    suspend fun getHomePhotos(page: Int): List<PhotosDTO>? {
        return client.request.getHomePhotos(page = page)
    }

    suspend fun getFoundPhotos(searchTerms: String, page: Int): List<PhotosDTO>? {
        return client.request.searchPhotos(query = searchTerms, page = page)?.results
    }

    suspend fun sendDownload(id: String) {
        client.request.downloadPhoto(id = id)
    }

    suspend fun getLikedPhotos(username: String, page: Int): List<PhotosDTO>? {
        val likedPhotos = client.request.getLikedPhotos(username = username, page = page)
        Log.d("LIKED PHOTOS", "$likedPhotos")
        Log.d("LIKED PHOTOS name", username)
        Log.d("LIKED PHOTOS page", "$page")
        return if (likedPhotos == null)
            null
        else
            likedPhotosMapper(likedPhotos)
    }

    suspend fun getToken(tokenBody: TokenBodyDTO): AuthInfoDTO? {
        val token = client.registrationRequest.getAccessToken(tokenData = tokenBody)?.access_token
        return if (token == null) {
            saveAuthState(false)
            null
        } else {
            saveToken(token)
            saveAuthState(true)
            client.registrationRequest.getAccessToken(tokenData = tokenBody)
        }
    }

    fun clearSharedPrefs() {
        getSharedPref().edit().clear().apply()
        getEncryptedSharedPref().edit().clear().apply()
    }

    fun clearLocalCache() {
        val cacheName = context.cacheDir.name
        context.deleteFile(cacheName)
    }

    private fun likedPhotosMapper(likedPhotos: List<LikedPhotosDTO>): List<PhotosDTO> {
        return likedPhotos.map {
            PhotosDTO(
                id = it.id,
                description = it.description,
                liked_by_user = it.liked_by_user,
                likes = it.likes,
                urls = it.urls,
                user = it.user
            )
        }
    }

    private fun getSharedPref(): SharedPreferences {
        return context.getSharedPreferences(
            Constants.KEY_APP_SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    private fun getEncryptedSharedPref(): SharedPreferences {
        val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            Constants.KEY_ENCRYPTED_SHARED_PREF,
            masterKeys,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun saveToken(token: String) {
        getEncryptedSharedPref().edit()
            .putString(Constants.KEY_TOKEN, token)
            .apply()
    }

    private fun saveAuthState(state: Boolean) {
        getSharedPref().edit()
            .putBoolean(Constants.KEY_IS_AUTHORIZED, state)
            .apply()
    }
}