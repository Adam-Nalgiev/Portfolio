package com.pets.insplash.data.network.dto

import com.pets.insplash.entity.SearchResult

data class SearchResultDTO(

    override val results: List<PhotosDTO>,

    ) : SearchResult
