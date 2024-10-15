package com.pets.insplash.presentation.collections.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pets.insplash.entity.dto.CollectionDTO

class CollectionDiffUtilCallback : DiffUtil.ItemCallback<CollectionDTO>() {

    override fun areItemsTheSame(oldItem: CollectionDTO, newItem: CollectionDTO): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CollectionDTO, newItem: CollectionDTO): Boolean = oldItem == newItem

}