package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.ProfileImage

data class ProfileImageDTO(

    override val small: String,
    override val medium: String,
    override val large: String,

    ) : ProfileImage
