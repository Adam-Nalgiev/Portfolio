package com.pets.insplash.entity.entities

interface Photos {

    val id: String
    val likes: Int
    val liked_by_user: Boolean?
    val description: String?
    val user: User
    val urls: Urls

}