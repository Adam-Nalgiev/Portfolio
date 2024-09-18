package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import javax.inject.Inject

class SendUnlikeUseCase @Inject constructor(private val repository: Repository) {
    suspend fun execute(id: String) {
        repository.sendUnlike(id)
    }
}