package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.PhotosDTO
import javax.inject.Inject

class GetFoundPhotosUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(searchTerms: String, page: Int): List<PhotosDTO> {
        return repository.getFoundPhotos(searchTerms, page)
    }

}