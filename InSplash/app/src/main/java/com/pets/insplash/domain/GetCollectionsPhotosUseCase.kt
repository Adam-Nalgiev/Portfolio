package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.Photos
import javax.inject.Inject

class GetCollectionsPhotosUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(id: String, page: Int): List<Photos>? {
        return repository.getCollectionsPhotos(id, page)
    }
}