package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.LikeUnlike

data class LikeUnlikeDTO(

    override val likesNumber: Int,
    override val liked_by_user: Boolean,

    ) : LikeUnlike