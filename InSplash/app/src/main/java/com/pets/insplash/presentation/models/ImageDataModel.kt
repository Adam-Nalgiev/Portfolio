package com.pets.insplash.presentation.models

import com.pets.insplash.entity.ImageData

data class ImageDataModel(

    override val id: String,
    override val imageUrl: String,
    override val profileImageUrl: String? = null,
    override val username: String,
    override val userLogin: String,
    override val likesCount: Int,
    override val isLikedByUser: Boolean = false,

    ) : ImageData
