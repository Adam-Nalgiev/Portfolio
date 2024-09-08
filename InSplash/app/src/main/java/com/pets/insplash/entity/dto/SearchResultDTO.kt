package com.pets.insplash.entity.dto

import com.pets.insplash.entity.entities.SearchResult

data class SearchResultDTO(

    override val results: List<PhotosDTO>,

    ) : SearchResult
