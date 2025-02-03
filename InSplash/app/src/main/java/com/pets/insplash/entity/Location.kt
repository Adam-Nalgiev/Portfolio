package com.pets.insplash.entity

import com.pets.insplash.data.network.dto.PositionDTO

interface Location {

    val city: String?
    val country: String?
    val position: PositionDTO?

}