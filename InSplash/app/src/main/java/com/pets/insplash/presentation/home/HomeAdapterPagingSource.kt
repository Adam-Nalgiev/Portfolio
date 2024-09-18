package com.pets.insplash.presentation.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pets.insplash.domain.GetHomePhotosUseCase
import com.pets.insplash.entity.dto.PhotosDTO

class HomeAdapterPagingSource : PagingSource<Int, PhotosDTO>() {

    override fun getRefreshKey(state: PagingState<Int, PhotosDTO>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosDTO> {
        val page = params.key ?: 1

        return runCatching {
            GetHomePhotosUseCase().execute(page)
        }.fold(
            onFailure = {
                LoadResult.Error(it)
            },
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    nextKey = page + 1,
                    prevKey = null
                )
            }
        )
    }
}