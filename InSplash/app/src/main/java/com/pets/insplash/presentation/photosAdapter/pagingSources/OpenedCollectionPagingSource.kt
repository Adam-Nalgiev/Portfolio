package com.pets.insplash.presentation.photosAdapter.pagingSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pets.insplash.domain.GetCollectionsPhotosUseCase
import com.pets.insplash.entity.Photos

class OpenedCollectionPagingSource(
    private val getCollectionsPhotosUseCase: GetCollectionsPhotosUseCase,
    private val id: String
) : PagingSource<Int, Photos>() {

    override fun getRefreshKey(state: PagingState<Int, Photos>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
        val page = params.key ?: 1

        return runCatching {
            getCollectionsPhotosUseCase.execute(id, page)
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