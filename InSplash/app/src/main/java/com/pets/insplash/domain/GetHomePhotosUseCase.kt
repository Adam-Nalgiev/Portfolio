package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.PhotosDTO

class GetHomePhotosUseCase {
    suspend fun execute(page: Int): List<PhotosDTO> {
        return Repository().getHomePhotos(page)
    }
}