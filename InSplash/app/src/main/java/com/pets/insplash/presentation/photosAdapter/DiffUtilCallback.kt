package com.pets.insplash.presentation.photosAdapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.pets.insplash.entity.Photos

object DiffUtilCallback : DiffUtil.ItemCallback<Photos>() {

    override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean =
        oldItem == newItem

}