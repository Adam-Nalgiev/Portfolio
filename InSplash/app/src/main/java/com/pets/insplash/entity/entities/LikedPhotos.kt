package com.pets.insplash.entity.entities

import com.pets.insplash.entity.dto.UrlsDTO
import com.pets.insplash.entity.dto.UserDTO

interface LikedPhotos {

    val id: String
    val created_at: String
    val updated_at: String
    val width: Int
    val height: Int
    val color: String
    val blur_hash: String
    val likes: Int
    val liked_by_user: Boolean?
    val description: String
    val user: UserDTO
    val urls: UrlsDTO

}