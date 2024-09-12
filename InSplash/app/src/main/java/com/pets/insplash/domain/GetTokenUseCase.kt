package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.TokenBodyDTO

class GetTokenUseCase {

    suspend fun execute(authData: TokenBodyDTO): AuthInfoDTO {
        return Repository().getToken(authData)
    }

}