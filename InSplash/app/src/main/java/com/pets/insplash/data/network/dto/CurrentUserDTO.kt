package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.CurrentUser

data class CurrentUserDTO(

    override val id: String,
    override val update_at: String?,
    override val username: String,
    override val first_name: String,
    override val last_name: String,
    override val twitter_username: String?,
    override val portfolio_url: String?,
    override val bio: String?,
    override val location: LocationDTO?,
    override val total_likes: Int,
    override val total_photos: Int,
    override val total_collections: Int,
    override val followed_by_user: Boolean,
    override val download: Int,
    override val uploads_remaining: Int,
    override val email: String?,
    override val links: LinksDTO,
    override val profile_image: ProfileImageDTO
    ) : CurrentUser
