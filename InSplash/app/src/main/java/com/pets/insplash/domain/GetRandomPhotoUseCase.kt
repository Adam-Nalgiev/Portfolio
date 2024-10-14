package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.OnePhotoDTO
import javax.inject.Inject

class GetRandomPhotoUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): OnePhotoDTO? {
        return repository.getRandomPhoto()
    }

}