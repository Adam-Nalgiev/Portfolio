package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.OnePhoto

data class OnePhotoDTO(

    override val id: String,
    override val created_at: String,
    override val updated_at: String,
    override val width: Int,
    override val height: Int,
    override val color: String,
    override val blur_hash: String,
    override val likes: Int,
    override val liked_by_user: Boolean,
    override val description: String,
    override val downloads: Int,
    override val public_domain: Boolean,
    override val exif: ExifDTO,
    override val location: LocationDTO?,
    override val tags: List<TagsDTO>,
    override val urls: UrlsDTO,
    override val links: LinksLocDTO,
    override val user: UserDTO,

    ) : OnePhoto


