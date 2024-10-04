package com.pets.insplash.data

import com.pets.insplash.data.network.NetworkClient
import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.dto.TokenBodyDTO
import javax.inject.Inject

class Repository @Inject constructor(private val client: NetworkClient) {

    suspend fun sendLike(photoId: String) {
        client.request.likePhoto(id = photoId)
    }

    suspend fun sendUnlike(photoId: String) {
        client.request.unlikePhoto(id = photoId)
    }

    suspend fun getPhoto(id: String): OnePhotoDTO {
        return client.request.getOnePhoto(id = id)
    }

    suspend fun getRandomPhoto(): OnePhotoDTO {
        return client.request.getRandomPhoto()
    }

    suspend fun getHomePhotos(page: Int): List<PhotosDTO> {
        return client.request.getHomePhotos(page = page)
    }

    suspend fun getFoundPhotos(searchTerms: String, page: Int): List<PhotosDTO> {
        return client.request.searchPhotos(query = searchTerms, page = page).results
    }

    suspend fun getToken(tokenBody: TokenBodyDTO): AuthInfoDTO {
        return client.registrationRequest.getAccessToken(tokenData = tokenBody)
    }

    suspend fun sendDownload(id: String) {
        client.request.downloadPhoto(id = id)
    }

}