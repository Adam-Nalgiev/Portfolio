package com.pets.insplash.presentation.screen.openedPhoto

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pets.insplash.domain.GetPhotoUseCase
import com.pets.insplash.domain.SendDownloadRequestUseCase
import com.pets.insplash.entity.OnePhoto
import com.pets.insplash.entity.Tags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class OpenedPhotosViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val sendDownloadRequestUseCase: SendDownloadRequestUseCase,
) : ViewModel() {

    suspend fun getPhoto(id: String): OnePhoto? {
        return viewModelScope.async(Dispatchers.IO) { getPhotoUseCase.execute(id) }.await()
    }

    suspend fun sendDownloadRequest(id: String): Boolean {
        return viewModelScope.async(Dispatchers.IO) {
            runCatching {
                sendDownloadRequestUseCase.execute(id)
            }.fold(
                onFailure = {
                    return@async false
                },
                onSuccess = {
                    return@async true
                }
            )
        }.await()
    }

    fun prepareRequest(url: String, name: String): DownloadManager.Request {
        return DownloadManager.Request(Uri.parse(url))
            .setTitle(name)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                System.currentTimeMillis().toString()
            )
            .setMimeType("image/jpeg")
    }

    fun prepareTags(tagsList: List<Tags>): String { //надо подумать над более алгоритмически эффективным решением
        val tags = mutableListOf<String>()
        tagsList.onEach {
            tags.add("#${it.title}")
        }
        return tags.toString().removePrefix("[").removeSuffix("]")
    }
}