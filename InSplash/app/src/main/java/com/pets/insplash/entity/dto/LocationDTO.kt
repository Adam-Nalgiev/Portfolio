package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.Location

data class LocationDTO(

    override val city: String?,
    override val country: String?,
    override val position: PositionDTO?,

    ) : Location