package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import javax.inject.Inject

class ClearLocalCacheUseCase @Inject constructor(private val repository: Repository) {
    fun execute() {
        repository.clearLocalCache()
    }
}