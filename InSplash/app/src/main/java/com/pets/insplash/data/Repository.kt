package com.pets.insplash.data

import com.pets.insplash.data.retrofit.NetworkClient
import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.dto.TokenBodyDTO
import javax.inject.Inject

class Repository @Inject constructor(private val header: String, private val client: NetworkClient) {

    suspend fun sendLike(photoId: String) {
        client.retrofit.likePhoto(request = header, id = photoId)
    }

    suspend fun sendUnlike(photoId: String) {
        client.retrofit.unlikePhoto(request = header, id = photoId)
    }

    suspend fun getHomePhotos(page: Int): List<PhotosDTO> {
        return client.retrofit.getHomePhotos(header, page)
    }

    suspend fun getToken(tokenBody: TokenBodyDTO): AuthInfoDTO {
        return client.retrofitReg.getAccessToken(tokenData = tokenBody)
    }

}