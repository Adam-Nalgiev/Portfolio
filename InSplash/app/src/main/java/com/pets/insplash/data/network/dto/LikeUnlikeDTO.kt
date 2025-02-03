package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.LikeUnlike

data class LikeUnlikeDTO(

    override val likesNumber: Int,
    override val liked_by_user: Boolean,

    ) : LikeUnlike