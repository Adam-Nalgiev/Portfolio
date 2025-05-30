package com.pets.insplash.presentation.photosAdapter.pagingSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pets.insplash.domain.GetLikedPhotosUseCase
import com.pets.insplash.entity.Photos

class LikedPhotosPagingSource(
    private val getLikedPhotosUseCase: GetLikedPhotosUseCase,
    private val username: String
) : PagingSource<Int, Photos>() {

    override fun getRefreshKey(state: PagingState<Int, Photos>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
        val page = params.key ?: 1

        return runCatching {
            getLikedPhotosUseCase.execute(username, page)
        }.fold(
            onFailure = {
                Log.d("PAGING Liked SOURCE", "FAIL $it")
                LoadResult.Error(it)
            },
            onSuccess = {
                return if (it != null) {
                    Log.d("PAGING liked SOURCE", "SUCCESS $it")
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