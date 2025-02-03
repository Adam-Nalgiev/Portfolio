package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.Links

data class LinksDTO(

    override val self: String,
    override val html: String,
    override val photos: String,
    override val likes: String,
    override val portfolio: String,

    ) : Links