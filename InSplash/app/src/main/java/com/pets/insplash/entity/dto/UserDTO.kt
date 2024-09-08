package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.User

data class UserDTO(

    override val id: String,
    override val username: String,
    override val name: String,
    override val profileImage: ProfileImageDTO?,

    ) : User