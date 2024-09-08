package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.AuthInfo

data class AuthInfoDTO(
    override val access_token: String,
): AuthInfo
