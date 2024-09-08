package com.pets.insplash.entity.entities

import com.pets.insplash.entity.dto.ProfileImageDTO

interface User {

    val id: String
    val username: String
    val name: String
    val profileImage: ProfileImageDTO?

}