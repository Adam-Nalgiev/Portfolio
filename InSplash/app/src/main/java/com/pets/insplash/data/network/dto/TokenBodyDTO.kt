package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.TokenBody

data class TokenBodyDTO(

    override val client_id: String = CLIENT_ID,
    override val client_secret: String = CLIENT_SECRET,
    override val redirect_uri: String = REDIRECT_URI,
    override val code: String,
    override val grant_type: String = REQUEST_GRANT_TYPE,

    ) : TokenBody {
        companion object {
            const val CLIENT_ID = "1uqN7imwX1vT0e-R2ALszCV2s0GA2gZT5vz2232tRFw"
            const val CLIENT_SECRET = "I56hShjIiIjYnVPKItVPcHHo6OiGO7JF_5EOpacTP-k"
            const val REDIRECT_URI = "com.pets.insplash://auth"
            const val REQUEST_GRANT_TYPE = "authorization_code"
        }
    }