package com.pets.insplash.presentation.collections.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

class CollectionsViewModelFactory @Inject constructor(private val viewModel: CollectionsViewModel): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel::class.java)) {
            return viewModel as T
        }else {
            throw IllegalArgumentException()
        }
    }

}