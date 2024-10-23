package com.pets.insplash.data

import com.pets.insplash.data.network.NetworkClient
import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.CollectionDTO
import com.pets.insplash.entity.dto.CurrentUserDTO
import com.pets.insplash.entity.dto.LikedPhotosDTO
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.dto.TokenBodyDTO
import javax.inject.Inject

class Repository @Inject constructor(private val client: NetworkClient) {

    suspend fun getMyProfile(token: String): CurrentUserDTO? {
        return client.request.getProfile("Bearer $token")
    }

    suspend fun getCollections(page: Int): List<CollectionDTO>? {
        return client.request.getCollections(page = page)
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

    suspend fun getToken(tokenBody: TokenBodyDTO): AuthInfoDTO? {
        return client.registrationRequest.getAccessToken(tokenData = tokenBody)
    }

    suspend fun sendDownload(id: String) {
        client.request.downloadPhoto(id = id)
    }

    suspend fun getLikedPhotos(username: String, page: Int): List<PhotosDTO>? {
        val likedPhotos = client.request.getLikedPhotos(username = username, page = page)

        if (likedPhotos == null)
            return null
        else
            return likedPhotosMapper(likedPhotos)
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
}