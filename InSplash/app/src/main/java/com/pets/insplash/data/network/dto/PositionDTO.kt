package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.Position

data class PositionDTO(

    override val latitude: Float,
    override val longitude: Float,

    ) : Position