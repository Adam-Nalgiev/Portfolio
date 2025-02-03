package com.pets.insplash.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.pets.insplash.data.network.NetworkClient
import com.pets.insplash.data.network.dto.LikedPhotosDTO
import com.pets.insplash.data.network.dto.PhotosDTO
import com.pets.insplash.data.network.dto.TokenBodyDTO
import com.pets.insplash.entity.AuthInfo
import com.pets.insplash.entity.Collections
import com.pets.insplash.entity.CurrentUser
import com.pets.insplash.entity.OnePhoto
import com.pets.insplash.entity.Photos
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: NetworkClient
) {

    suspend fun getMyProfile(): CurrentUser? {
        val token = getEncryptedSharedPref().getString(KEY_TOKEN, "")
        return client.request.getProfile(request = "Bearer $token")
    }

    suspend fun getCollections(page: Int): List<Collections>? {
        return client.request.getCollections(page = page)
    }

    suspend fun getCollectionsPhotos(id: String, page: Int): List<Photos>? {
        return client.request.getCollectionPhotos(id = id, page = page)
    }

    suspend fun sendLike(photoId: String) {
        val token = getEncryptedSharedPref().getString(KEY_TOKEN, "")
        client.request.likePhoto(request = "Bearer $token", id = photoId)
    }

    suspend fun sendUnlike(photoId: String) {
        val token = getEncryptedSharedPref().getString(KEY_TOKEN, "")
        client.request.unlikePhoto(request = "Bearer $token", id = photoId)
    }

    suspend fun getPhoto(id: String): OnePhoto? {
        return client.request.getOnePhoto(id = id)
    }

    suspend fun getRandomPhoto(): OnePhoto? {
        return client.request.getRandomPhoto()
    }

    suspend fun getHomePhotos(page: Int): List<Photos>? {
        return client.request.getHomePhotos(page = page)
    }

    suspend fun getFoundPhotos(searchTerms: String, page: Int): List<Photos>? {
        return client.request.searchPhotos(query = searchTerms, page = page)?.results
    }

    suspend fun sendDownload(id: String) {
        client.request.downloadPhoto(id = id)
    }

    suspend fun getLikedPhotos(username: String, page: Int): List<Photos>? {
        val likedPhotos = client.request.getLikedPhotos(username = username, page = page)
        return if (likedPhotos == null)
            null
        else
            likedPhotosMapper(likedPhotos)
    }

    suspend fun getToken(tokenBody: TokenBodyDTO): AuthInfo? {
        val token = client.registrationRequest.getAccessToken(tokenData = tokenBody)?.access_token
        Log.d("TOKEN!!!!", "$token")
        return if (token == null) {
            saveAuthState(false)
            null
        } else {
            saveToken(token)
            saveAuthState(true)
            client.registrationRequest.getAccessToken(tokenData = tokenBody)
        }
    }

    fun getAuthState(): Boolean {
        return getSharedPref().getBoolean(KEY_IS_AUTHORIZED, false)
    }

    fun clearSharedPrefs() {
        getSharedPref().edit().clear().apply()
        getEncryptedSharedPref().edit().clear().apply()
    }

    fun clearLocalCache() {
        val cacheName = context.cacheDir.name
        context.deleteFile(cacheName)
    }

    private fun likedPhotosMapper(likedPhotos: List<LikedPhotosDTO>): List<Photos> {
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
            KEY_APP_SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    private fun getEncryptedSharedPref(): SharedPreferences {
        val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            KEY_ENCRYPTED_SHARED_PREF,
            masterKeys,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun saveToken(token: String) {
        getEncryptedSharedPref().edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    private fun saveAuthState(state: Boolean) {
        getSharedPref().edit()
            .putBoolean(KEY_IS_AUTHORIZED, state)
            .apply()
    }

    companion object {
        private const val KEY_TOKEN = "App token"
        private const val KEY_APP_SHARED_PREF = "InSplash shared preferences"
        private const val KEY_ENCRYPTED_SHARED_PREF = "Secret data"
        private const val KEY_IS_AUTHORIZED = "Is authorized"
    }
}