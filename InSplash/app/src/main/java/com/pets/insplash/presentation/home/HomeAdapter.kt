package com.pets.insplash.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pets.insplash.R
import com.pets.insplash.databinding.ItemPhotosBinding
import com.pets.insplash.domain.SendLikeUseCase
import com.pets.insplash.domain.SendUnlikeUseCase
import com.pets.insplash.entity.dto.PhotosDTO

class HomeAdapter(private val onClick: (String) -> Unit) :
    PagingDataAdapter<PhotosDTO, MainViewHolder>(DiffUtilCallback()) {

    private lateinit var bind: ItemPhotosBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        bind = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(bind)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)!!
        with(holder.binding) {
            item.let { it ->
                var isLiked = false
                username.text = it.user.name
                login.text = "@${it.user.username}"
                likes.text = it.likes.toString()

                Glide.with(image).load(it.urls.regular).error(R.drawable.icon_error).into(image)

                Glide.with(imageProfile).load(it.user.profile_image?.medium)
                    .error(R.drawable.icon_empty_profile).circleCrop().into(imageProfile)

                image.setOnClickListener {
                    onClick(item.id)
                }

                buttonLike.setOnClickListener {

                    if (!isLiked) {
                        Glide.with(root).load(R.drawable.icon_like_active).into(buttonLike)
                        Log.d("SEND LIKE", "SEND LIKE ${it.id}")
                        isLiked = true
                        suspend { sendLike(it.id.toString()) }
                    } else {
                        Glide.with(root).load(R.drawable.icon_like_inactive).into(buttonLike)
                        Log.d("SEND UNLIKE", "SEND UNLIKE ${it.id}")
                        isLiked = false
                        suspend { sendUnlike(it.id.toString()) }
                    }
                }
            }
        }
    }

    private suspend fun sendUnlike(id: String) {
        SendUnlikeUseCase().execute(id)
    }

    private suspend fun sendLike(id: String) {
        SendLikeUseCase().execute(id)
    }
}

class MainViewHolder(val binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<PhotosDTO>() {

    override fun areItemsTheSame(oldItem: PhotosDTO, newItem: PhotosDTO): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PhotosDTO, newItem: PhotosDTO): Boolean = oldItem == newItem

}