package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.User

data class UserDTO(

    override val id: String,
    override val username: String,
    override val name: String,
    override val profile_image: ProfileImageDTO?,

    ) : User