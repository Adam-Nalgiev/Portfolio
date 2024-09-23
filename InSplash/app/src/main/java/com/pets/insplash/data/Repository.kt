package com.pets.insplash.data

import com.pets.insplash.data.retrofit.NetworkClient
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.dto.TokenBodyDTO

class Repository {

    private val client = NetworkClient()

    private var header = "Client-ID ${Constants.CLIENT_ID}"

    suspend fun sendLike(photoId: String) {
        client.retrofit.likePhoto(header, id = photoId)
    }

    suspend fun sendUnlike(photoId: String) {
        client.retrofit.unlikePhoto(header, id = photoId)
    }

    suspend fun getRandomPhoto(): OnePhotoDTO {
        return client.retrofit.getRandomPhoto(header)
    }

    suspend fun getHomePhotos(page: Int): List<PhotosDTO> {
        return client.retrofit.getHomePhotos(header, page)
    }

    suspend fun getFoundPhotos(searchTerms: String, page: Int): List<PhotosDTO> {
        return client.retrofit.searchPhotos(header, query = searchTerms, page = page).results
    }

    suspend fun getToken(tokenBody: TokenBodyDTO): AuthInfoDTO {
        return client.retrofitReg.getAccessToken(tokenData = tokenBody)
    }


}