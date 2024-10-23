package com.pets.insplash.presentation.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(private val viewModel: ProfileViewModel): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel::class.java)) {
            return viewModel as T
        }else {
            throw IllegalArgumentException()
        }
    }
}