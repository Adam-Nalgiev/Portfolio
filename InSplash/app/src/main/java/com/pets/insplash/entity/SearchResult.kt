package com.pets.insplash.entity

import com.pets.insplash.data.network.dto.PhotosDTO

interface SearchResult {

    val results: List<PhotosDTO>

}