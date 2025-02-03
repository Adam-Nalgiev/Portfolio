package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.Urls

data class UrlsDTO(

    override val raw: String?,
    override val full: String?,
    override val regular: String?,
    override val small: String,
    override val thumb: String,

    ) : Urls