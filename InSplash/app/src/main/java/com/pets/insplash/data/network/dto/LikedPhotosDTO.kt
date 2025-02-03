package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.LikedPhotos

data class LikedPhotosDTO(

    override val id: String,
    override val created_at: String,
    override val updated_at: String,
    override val width: Int,
    override val height: Int,
    override val color: String,
    override val blur_hash: String,
    override val likes: Int,
    override val liked_by_user: Boolean? = false,
    override val description: String,
    override val user: UserDTO,
    override val urls: UrlsDTO,

    ) : LikedPhotos