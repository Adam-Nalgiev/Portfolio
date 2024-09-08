package com.pets.insplash.entity.entities

import com.pets.insplash.entity.dto.PositionDTO

interface Location {

    val city: String?
    val country: String?
    val position: PositionDTO?

}