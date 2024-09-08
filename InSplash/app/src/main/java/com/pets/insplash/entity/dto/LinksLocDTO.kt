package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.LinksLoc

data class LinksLocDTO(

    override val self: String,
    override val html: String,
    override val download: String,
    override val download_location: String,

    ) : LinksLoc