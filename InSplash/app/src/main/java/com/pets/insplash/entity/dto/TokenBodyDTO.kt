package com.pets.insplash.entity.dto

import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.entities.TokenBody

data class TokenBodyDTO(

    override val client_id: String = Constants.CLIENT_ID,
    override val client_secret: String = Constants.CLIENT_SECRET,
    override val redirect_uri: String = Constants.REDIRECT_URI,
    override val code: String,
    override val grant_type: String = Constants.REQUEST_GRANT_TYPE,

    ) : TokenBody