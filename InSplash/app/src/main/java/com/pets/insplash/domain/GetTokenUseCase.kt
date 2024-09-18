package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.TokenBodyDTO
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(authData: TokenBodyDTO): AuthInfoDTO {
        return repository.getToken(authData)
    }

}