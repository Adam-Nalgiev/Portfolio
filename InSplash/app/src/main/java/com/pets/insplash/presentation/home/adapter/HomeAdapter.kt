package com.pets.insplash.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.pets.insplash.R
import com.pets.insplash.databinding.ItemPhotosBinding
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.presentationModels.ClickAction
import javax.inject.Inject

class HomeAdapter @Inject constructor(private val onClick: (ClickAction, String) -> Unit) :
    PagingDataAdapter<PhotosDTO, HomeAdapterViewHolder>(DiffUtilCallback()) {

    private lateinit var bind: ItemPhotosBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder {
        bind = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeAdapterViewHolder(bind)
    }

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        val item = getItem(position)!!
        with(holder.binding) {
            item.let {
                val profileImageUrl = it.user.profile_image?.medium ?: ""
                val username = it.user.name
                val login = "@${it.user.username}"
                val likesCount = it.likes

                viewUserProfile.setImage(profileImageUrl)
                viewUserProfile.setUsername(username)
                viewUserProfile.setUserLogin(login)

                viewLikes.setLikesCount(likesCount)

                Glide
                    .with(image)
                    .load(it.urls.regular)
                    .error(R.drawable.icon_error)
                    .into(image)

                image.setOnClickListener {
                    onClick(ClickAction.OPEN_ITEM, item.id)
                }

            }
        }
    }

}