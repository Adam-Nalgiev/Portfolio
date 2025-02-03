package com.pets.insplash.domain

import com.pets.insplash.data.Repository
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(private val repository: Repository) {

    fun execute(): Boolean {
        return repository.getAuthState()
    }
}