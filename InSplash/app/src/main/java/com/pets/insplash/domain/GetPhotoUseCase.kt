package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.OnePhoto
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(id: String): OnePhoto? {
        return repository.getPhoto(id)
    }
}