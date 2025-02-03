package com.pets.insplash.entity

interface ImageData {

    val id:String
    val imageUrl: String
    val profileImageUrl: String?
    val username: String
    val userLogin: String
    val likesCount: Int
    val isLikedByUser: Boolean

}