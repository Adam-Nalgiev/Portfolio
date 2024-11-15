package com.pets.insplash.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.pets.insplash.R
import com.pets.insplash.databinding.ViewLikesBinding
import com.pets.insplash.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikesViewGroup
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewLikesBinding.inflate(LayoutInflater.from(context))
    private var isLiked = false
    private var likesCount = 0
    private var id: String? = null

    @Volatile
    lateinit var viewModel: HomeViewModel

//    @Inject
//    lateinit var viewModelFactory: HomeViewModelFactory

//    private val viewModel by lazy {
//        findViewTreeViewModelStoreOwner()?.let { owner ->
//            ViewModelProvider(
//                owner,
//                viewModelFactory
//            )[HomeViewModel::class.java]
//        }
//    }

    init {
        addView(binding.root)
        setClickListener()
    }

    fun setLikesCount(count: Int) {
        likesCount = count
        binding.likes.text = "$likesCount"
    }

    fun setLikesState(isLikedByUser: Boolean, photoId: String) {
        isLiked = isLikedByUser
        id = photoId

        if (isLikedByUser) {
            setLike()
        } else {
            setUnlike()
        }
    }

    private fun setLike() {
        Log.d("LIKE ID", "$id")
        isLiked = true
        setLikesCount(++likesCount)
        Glide.with(context).load(R.drawable.icon_like_active).into(binding.buttonLike)
    }

    private fun setUnlike() {
        isLiked = false
        setLikesCount(--likesCount)
        Glide.with(context).load(R.drawable.icon_like_inactive).into(binding.buttonLike)
    }

    private fun onClick() {
        if (!isLiked) {
            setLike()
            if (id != null) viewModel.sendLike(id!!)
        } else {
            setUnlike()
            if (id != null) viewModel.sendUnlike(id!!)
        }
    }

    private fun setClickListener() {
        binding.buttonLike.setOnClickListener {
            onClick()
        }
    }
}