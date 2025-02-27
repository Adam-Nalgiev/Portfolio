package com.pets.insplash.entity

import com.pets.insplash.data.network.dto.OnePhotoDTO
import com.pets.insplash.data.network.dto.UrlsDTO
import com.pets.insplash.data.network.dto.UserDTO

interface Collections {

    val id: String
    val title: String
    val description: String?
    val published_at: String
    val last_collected: String?
    val update_at: String?
    val total_photos: Int
    val private: Boolean
    val share_key: String
    val cover_photo: OnePhotoDTO
    val user: UserDTO
    val urls: UrlsDTO?

}