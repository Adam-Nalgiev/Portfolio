package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.OnePhotoDTO

class GetRandomPhotoUseCase {
    suspend fun execute(): OnePhotoDTO {
        return Repository().getRandomPhoto()
    }
}