package com.pets.insplash.presentation.collections.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.pets.insplash.databinding.ItemCollectionBinding
import com.pets.insplash.entity.dto.CollectionDTO
import javax.inject.Inject

class CollectionsAdapter @Inject constructor(private val onClick: (String) -> Unit) :
    PagingDataAdapter<CollectionDTO, CollectionsViewHolder>(CollectionDiffUtilCallback()) {

    private lateinit var bind: ItemCollectionBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        bind = ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionsViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        val item = getItem(position)!!
        with(holder.binding) {
            item.let {
                val id = it.id
                val profileImageUrl = it.user.profile_image?.medium ?: ""
                val username = it.user.name
                val login = "@${it.user.username}"
                val photosCount = it.total_photos
                val title = it.title

                Glide.with(image).load(it.cover_photo).into(image)

                textPhotosCount.text = photosCount.toString()

                textCollectionName.text = title

                viewUserProfile.setImage(profileImageUrl)
                viewUserProfile.setUsername(username)
                viewUserProfile.setUserLogin(login)

                image.setOnClickListener{
                    onClick(id)
                }

            }
        }
    }


}