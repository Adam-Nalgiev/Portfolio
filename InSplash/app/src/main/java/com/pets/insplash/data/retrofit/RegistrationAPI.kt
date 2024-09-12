package com.pets.insplash.data.retrofit

import com.pets.insplash.entity.dto.AuthInfoDTO
import com.pets.insplash.entity.dto.TokenBodyDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationAPI {

    @POST("/oauth/token")
    suspend fun getAccessToken(
        @Body tokenData: TokenBodyDTO
    ): AuthInfoDTO

}