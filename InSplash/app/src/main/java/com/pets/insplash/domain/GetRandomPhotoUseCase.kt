package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.OnePhoto
import javax.inject.Inject

class GetRandomPhotoUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): OnePhoto? {
        return repository.getRandomPhoto()
    }
}