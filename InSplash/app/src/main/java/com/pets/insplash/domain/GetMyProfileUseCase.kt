package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.CurrentUser
import javax.inject.Inject

class GetMyProfileUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): CurrentUser? {
        return repository.getMyProfile()
    }
}