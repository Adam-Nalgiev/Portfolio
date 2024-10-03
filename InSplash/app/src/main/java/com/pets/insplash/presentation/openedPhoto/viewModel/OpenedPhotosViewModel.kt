package com.pets.insplash.presentation.openedPhoto.viewModel

import androidx.lifecycle.ViewModel
import com.pets.insplash.domain.GetPhotoUseCase
import com.pets.insplash.entity.dto.OnePhotoDTO
import javax.inject.Inject

class OpenedPhotosViewModel @Inject constructor(private val getPhotoUseCase: GetPhotoUseCase) : ViewModel() {


    suspend fun getPhoto(id: String): OnePhotoDTO {
        return getPhotoUseCase.execute(id)
    }
}