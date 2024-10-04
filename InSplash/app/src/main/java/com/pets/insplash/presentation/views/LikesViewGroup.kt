package com.pets.insplash.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.pets.insplash.R
import com.pets.insplash.databinding.ViewLikesBinding

class LikesViewGroup
    @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewLikesBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setLikesCount(count: Int) {
        binding.likes.text = count.toString()
    }

    fun setLike() {
        Glide.with(context).load(R.drawable.icon_like_active).into(binding.buttonLike)
    }

    fun setUnlike() {
        Glide.with(context).load(R.drawable.icon_like_inactive).into(binding.buttonLike)
    }
}