package com.pets.insplash.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.pets.insplash.R
import com.pets.insplash.databinding.ViewUserProfileBinding

class UserProfileViewGroup
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewUserProfileBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setImage(url: String) {
        Glide
            .with(context)
            .load(url)
            .error(R.drawable.icon_empty_profile)
            .circleCrop()
            .into(binding.imageProfile)
    }

    fun setUsername(username: String) {
        binding.username.text = username
    }

    fun setUserLogin(login: String) {
        binding.login.text = login
    }

}