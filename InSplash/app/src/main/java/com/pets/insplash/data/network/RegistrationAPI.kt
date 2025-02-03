package com.pets.insplash.data.network

import com.pets.insplash.data.network.dto.AuthInfoDTO
import com.pets.insplash.data.network.dto.TokenBodyDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationAPI {

    @POST("/oauth/token")
    suspend fun getAccessToken(
        @Body tokenData: TokenBodyDTO
    ): AuthInfoDTO?

}