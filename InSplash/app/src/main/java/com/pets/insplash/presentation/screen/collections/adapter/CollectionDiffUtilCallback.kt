package com.pets.insplash.presentation.screen.collections.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.pets.insplash.entity.Collections

class CollectionDiffUtilCallback : DiffUtil.ItemCallback<Collections>() {

    override fun areItemsTheSame(oldItem: Collections, newItem: Collections): Boolean = oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Collections, newItem: Collections): Boolean = oldItem == newItem

}