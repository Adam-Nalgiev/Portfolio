package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.CollectionDTO
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(page: Int): List<CollectionDTO>? {
        return repository.getCollections(page = page)
    }

}