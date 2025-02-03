package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.data.network.dto.TokenBodyDTO
import com.pets.insplash.entity.AuthInfo
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(authData: TokenBodyDTO): AuthInfo? {
        return repository.getToken(authData)
    }
}