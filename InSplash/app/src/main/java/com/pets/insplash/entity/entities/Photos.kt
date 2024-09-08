package com.pets.insplash.entity.entities

import com.pets.insplash.entity.dto.UrlsDTO
import com.pets.insplash.entity.dto.UserDTO

interface Photos {

    val id: String
    val likes: Int
    val liked_by_user: Boolean?
    val description: String?
    val user: UserDTO
    val urls: UrlsDTO

}