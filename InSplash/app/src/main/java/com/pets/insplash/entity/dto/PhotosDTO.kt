package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.Photos

data class PhotosDTO(

    override val id: String,
    override val likes: Int,
    override val liked_by_user: Boolean? = null,
    override val description: String? = null,
    override val user: UserDTO,
    override val urls: UrlsDTO,

    ) : Photos

