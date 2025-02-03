package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.Location

data class LocationDTO(

    override val city: String?,
    override val country: String?,
    override val position: PositionDTO?,

    ) : Location