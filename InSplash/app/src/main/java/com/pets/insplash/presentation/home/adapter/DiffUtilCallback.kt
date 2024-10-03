package com.pets.insplash.presentation.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pets.insplash.entity.dto.PhotosDTO

class DiffUtilCallback : DiffUtil.ItemCallback<PhotosDTO>() {

    override fun areItemsTheSame(oldItem: PhotosDTO, newItem: PhotosDTO): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PhotosDTO, newItem: PhotosDTO): Boolean = oldItem == newItem

}