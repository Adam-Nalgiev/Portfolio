package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.Exif

data class ExifDTO(

    override val make: String,
    override val model: String,
    override val name: String,
    override val exposure_time: String,
    override val aperture: String,
    override val focal_length: Int,
    override val iso: Int,

    ) : Exif