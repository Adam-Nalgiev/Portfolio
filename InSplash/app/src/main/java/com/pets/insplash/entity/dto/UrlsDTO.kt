package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.Urls

data class UrlsDTO(

    override val raw: String?,
    override val full: String?,
    override val regular: String?,
    override val small: String,
    override val thumb: String,

    ) : Urls