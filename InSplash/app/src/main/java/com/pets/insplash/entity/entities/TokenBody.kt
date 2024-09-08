package com.pets.insplash.entity.entities

interface TokenBody {

    val client_id: String
    val client_secret: String
    val redirect_uri: String
    val code: String
    val grant_type: String

}