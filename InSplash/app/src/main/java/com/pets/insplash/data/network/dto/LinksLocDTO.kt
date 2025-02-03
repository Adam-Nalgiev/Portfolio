package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.LinksLoc

data class LinksLocDTO(

    override val self: String,
    override val html: String,
    override val download: String,
    override val download_location: String,

    ) : LinksLoc