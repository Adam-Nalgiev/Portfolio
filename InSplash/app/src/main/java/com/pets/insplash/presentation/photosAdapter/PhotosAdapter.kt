package com.pets.insplash.presentation.photosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.pets.insplash.R
import com.pets.insplash.databinding.ItemPhotosBinding
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.presentationModels.ClickAction
import javax.inject.Inject

class PhotosAdapter @Inject constructor(private val onClick: (ClickAction, String) -> Unit) :
    PagingDataAdapter<PhotosDTO, PhotosViewHolder>(DiffUtilCallback()) {

    private lateinit var bind: ItemPhotosBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        bind = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(bind)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val item = getItem(position)!!
        with(holder.binding) {
            item.let {
                val profileImageUrl = it.user.profile_image?.medium ?: ""
                val username = it.user.name
                val login = "@${it.user.username}"
                val likesCount = it.likes
                val isLiked = it.liked_by_user ?: false

                viewUserProfile.setImage(profileImageUrl)
                viewUserProfile.setUsername(username)
                viewUserProfile.setUserLogin(login)

                viewLikes.setLikesCount(likesCount)
                viewLikes.setLikesState(isLiked)

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