package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.CurrentUserDTO
import javax.inject.Inject

class GetMyProfileUserCase @Inject constructor(private val repository: Repository) {
    suspend fun execute(token: String): CurrentUserDTO? {
        return repository.getMyProfile(token)
    }
}