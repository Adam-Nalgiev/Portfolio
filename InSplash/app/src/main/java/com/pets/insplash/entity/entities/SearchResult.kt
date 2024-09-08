package com.pets.insplash.entity.entities

import com.pets.insplash.entity.dto.PhotosDTO

interface SearchResult {

    val results: List<PhotosDTO>

}