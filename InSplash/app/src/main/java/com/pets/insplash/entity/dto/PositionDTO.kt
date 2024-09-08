package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.Position

data class PositionDTO(

    override val latitude: Float,
    override val longitude: Float,

    ) : Position