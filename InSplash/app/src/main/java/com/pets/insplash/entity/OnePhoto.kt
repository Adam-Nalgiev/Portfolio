package com.pets.insplash.entity

import com.pets.insplash.data.network.dto.ExifDTO
import com.pets.insplash.data.network.dto.LinksLocDTO
import com.pets.insplash.data.network.dto.LocationDTO
import com.pets.insplash.data.network.dto.TagsDTO
import com.pets.insplash.data.network.dto.UrlsDTO
import com.pets.insplash.data.network.dto.UserDTO

interface OnePhoto {

    val id: String
    val created_at: String
    val updated_at: String
    val width: Int
    val height: Int
    val color: String
    val blur_hash: String
    val likes: Int
    val liked_by_user: Boolean?
    val description: String
    val downloads: Int
    val public_domain: Boolean
    val exif: ExifDTO
    val location: LocationDTO?
    val tags: List<TagsDTO>
    val urls: UrlsDTO
    val links: LinksLocDTO
    val user: UserDTO

}