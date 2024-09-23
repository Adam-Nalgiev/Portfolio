package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.PhotosDTO

class GetFoundPhotosUseCase {
    suspend fun execute(searchTerms: String, page: Int): List<PhotosDTO> {
        return Repository().getFoundPhotos(searchTerms, page)
    }
}