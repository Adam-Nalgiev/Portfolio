package com.pets.insplash.presentation.collections.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

                setImage(holder.binding, it.cover_photo.urls.regular)

                Log.d("COLLECTIONS", "${it.cover_photo.urls.regular}")

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

    private fun setImage(binding:ItemCollectionBinding, link: String?) {

        Glide.with(binding.image).load(link).centerCrop().listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.root.visibility = View.GONE
                    binding.progressCircular.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressCircular.visibility = View.GONE
                    return false
                }
            }
        ).centerCrop().into(binding.image)
    }

}