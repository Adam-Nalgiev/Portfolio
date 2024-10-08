package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.Photos

data class PhotosDTO(

    override val id: String,
    override val likes: Int,
    override val liked_by_user: Boolean?,
    override val description: String?,
    override val user: UserDTO,
    override val urls: UrlsDTO,

    ) : Photos

