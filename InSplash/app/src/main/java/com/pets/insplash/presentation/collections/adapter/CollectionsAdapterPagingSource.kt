package com.pets.insplash.presentation.collections.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pets.insplash.domain.GetCollectionsUseCase
import com.pets.insplash.entity.dto.CollectionDTO
import javax.inject.Inject

class CollectionsAdapterPagingSource (
    private val getCollectionsUseCase: GetCollectionsUseCase
): PagingSource<Int, CollectionDTO>() {

    override fun getRefreshKey(state: PagingState<Int, CollectionDTO>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionDTO> {
        val page = params.key ?: 1

        return runCatching {
            getCollectionsUseCase.execute(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                data = it!!,
                nextKey = page + 1,
                prevKey = null
            )},
            onFailure = {LoadResult.Error(it)}
        )
    }
}