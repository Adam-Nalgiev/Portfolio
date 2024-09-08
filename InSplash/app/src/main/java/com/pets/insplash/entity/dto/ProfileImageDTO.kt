package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.ProfileImage

data class ProfileImageDTO(

    override val small: String,
    override val medium: String,
    override val large: String,

    ) : ProfileImage
