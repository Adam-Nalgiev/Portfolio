package com.pets.insplash.data

import com.pets.insplash.data.retrofit.NetworkClient
import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.TokenBodyDTO

class Repository {


    suspend fun getToken(tokenBody: TokenBodyDTO): AuthInfoDTO {
        return NetworkClient.retrofitReg.getAccessToken(tokenData = tokenBody)
    }

}