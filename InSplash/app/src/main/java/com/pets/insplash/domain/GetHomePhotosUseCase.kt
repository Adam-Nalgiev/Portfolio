package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.Photos
import javax.inject.Inject

class GetHomePhotosUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(page: Int): List<Photos>? {
        return repository.getHomePhotos(page)
    }
}