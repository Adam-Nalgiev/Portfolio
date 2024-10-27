package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.CurrentUserDTO
import javax.inject.Inject

class GetMyProfileUseCase @Inject constructor(private val repository: Repository) {
    suspend fun execute(): CurrentUserDTO? {
        return repository.getMyProfile()
    }
}