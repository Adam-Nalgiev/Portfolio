package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.Exif

data class ExifDTO(

    override val make: String,
    override val model: String,
    override val name: String,
    override val exposure_time: String,
    override val aperture: String,
    override val focal_length: Float,
    override val iso: Int,

    ) : Exif