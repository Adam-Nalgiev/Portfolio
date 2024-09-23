package com.pets.insplash.domain

import com.pets.insplash.data.Repository

class SendUnlikeUseCase {
    suspend fun execute(id: String) {
        Repository().sendUnlike(id)
    }
}