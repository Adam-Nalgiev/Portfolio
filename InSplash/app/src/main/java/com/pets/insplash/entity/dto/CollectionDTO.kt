package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.Collections

data class CollectionDTO(

    override val id: String,
    override val title: String,
    override val description: String?,
    override val published_at: String,
    override val last_collected: String?,
    override val update_at: String?,
    override val total_photos: Int,
    override val private: Boolean,
    override val share_key: String,
    override val cover_photo: OnePhotoDTO,
    override val user: UserDTO,
    override val urls: UrlsDTO?,

    ) : Collections
