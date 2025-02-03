package com.pets.insplash.presentation.photosAdapter.pagingSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pets.insplash.domain.GetFoundPhotosUseCase
import com.pets.insplash.entity.Photos

class FoundPhotosPagingSource(
    private val getFoundPhotosUseCase: GetFoundPhotosUseCase,
    private val requestText: String,
) : PagingSource<Int, Photos>() {

    override fun getRefreshKey(state: PagingState<Int, Photos>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
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