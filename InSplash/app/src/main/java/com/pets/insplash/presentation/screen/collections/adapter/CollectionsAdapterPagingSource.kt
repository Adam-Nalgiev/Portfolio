package com.pets.insplash.presentation.screen.collections.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pets.insplash.domain.GetCollectionsUseCase
import com.pets.insplash.entity.Collections

class CollectionsAdapterPagingSource(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : PagingSource<Int, Collections>() {

    override fun getRefreshKey(state: PagingState<Int, Collections>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collections> {
        val page = params.key ?: 1

        return runCatching {
            getCollectionsUseCase.execute(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it!!,
                    nextKey = page + 1,
                    prevKey = null
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }
}