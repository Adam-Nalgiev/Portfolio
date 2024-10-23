package com.pets.insplash.entity.entities

import com.pets.insplash.entity.dto.LinksDTO
import com.pets.insplash.entity.dto.LocationDTO

interface CurrentUser {

    val id: String
    val update_at: String?
    val username: String
    val first_name: String
    val last_name: String
    val twitter_username: String?
    val portfolio_url: String?
    val bio: String?
    val location: LocationDTO?
    val total_likes: Int
    val total_photos: Int
    val total_collections: Int
    val followed_by_user: Boolean
    val download: Int
    val uploads_remaining: Int
    val email: String?
    val links: LinksDTO
    val profile_image: ProfileImage
}