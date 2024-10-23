package com.pets.insplash.presentation.photosAdapter.pagingSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pets.insplash.domain.GetFoundPhotosUseCase
import com.pets.insplash.entity.dto.PhotosDTO

class FoundPhotosPagingSource(
    private val getFoundPhotosUseCase: GetFoundPhotosUseCase,
    private val requestText: String,
) : PagingSource<Int, PhotosDTO>() {

    override fun getRefreshKey(state: PagingState<Int, PhotosDTO>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosDTO> {
        val page = params.key ?: 1

        return runCatching {
            getFoundPhotosUseCase.execute(requestText, page)
        }.fold(
            onFailure = {
                Log.d("PAGING SOURCE", "FAIL $it")
                LoadResult.Error(it)
            },
            onSuccess = {
                return if (it != null) {
                    Log.d("PAGING SOURCE", "SUCCESS $it")
                    LoadResult.Page(
                        data = it,
                        nextKey = page + 1,
                        prevKey = null
                    )
                } else {
                    LoadResult.Error(NullPointerException())
                }
            }
        )
    }
}