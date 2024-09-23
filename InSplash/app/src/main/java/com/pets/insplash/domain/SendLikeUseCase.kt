package com.pets.insplash.domain

import com.pets.insplash.data.Repository

class SendLikeUseCase {
    suspend fun execute(id: String) {
       Repository().sendLike(id)
    }
}