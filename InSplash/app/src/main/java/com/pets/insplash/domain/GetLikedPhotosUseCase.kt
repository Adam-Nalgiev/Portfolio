package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.Photos
import javax.inject.Inject

class GetLikedPhotosUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(username: String, page: Int): List<Photos>? {
        return repository.getLikedPhotos(username, page)
    }
}