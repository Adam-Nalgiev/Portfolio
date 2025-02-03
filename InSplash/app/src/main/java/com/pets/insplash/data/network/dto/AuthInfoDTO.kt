package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.AuthInfo

data class AuthInfoDTO(
    override val access_token: String,
): AuthInfo
