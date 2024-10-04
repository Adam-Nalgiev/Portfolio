package com.pets.insplash.presentation.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentHomeBinding
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.presentationModels.ClickAction
import com.pets.insplash.entity.presentationModels.ImageDataModel
import com.pets.insplash.presentation.home.adapter.HomeAdapter
import com.pets.insplash.presentation.home.viewModel.HomeViewModel
import com.pets.insplash.presentation.home.viewModel.HomeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter: HomeAdapter = HomeAdapter { action, id -> onClick(action, id) }

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels{ viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val state = viewModel.authState(context)
        if (!state)
            findNavController().navigate(R.id.action_homeFragment_to_authorizationFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerHomePhotos.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            val photosData = viewModel.imageDataMapper()

            if (photosData != null) {
                setImage(photosData)

                binding.imageInteresting.setOnClickListener {
                    onClick(ClickAction.OPEN_ITEM, photosData.id)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collect {
                adapter.submitData(it)
            }

            adapter.loadStateFlow.collect {
                binding.progressLinear.isVisible = it.prepend is LoadState.Loading
            }
        }

        binding.textInputSearch.setEndIconOnClickListener {
            binding.editTextSearch.isVisible = !binding.editTextSearch.isVisible
            binding.textInputSearch.isHintEnabled = binding.editTextSearch.isVisible
        }

        binding.progressCircular.setOnClickListener {
            binding.progressCircular.isVisible = !binding.progressCircular.isVisible
        }

        binding.editTextSearch.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                viewLifecycleOwner.lifecycleScope.launch {
                    if (binding.editTextSearch.text.toString() == "") {
                        viewModel.changeRecyclerData(null)
                        viewModel.photos.collect{ adapter.submitData(it)}
                    } else {
                        viewModel.changeRecyclerData(binding.editTextSearch.text.toString())
                        viewModel.photos.collect{ adapter.submitData(it)}
                    }
                }
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onClick(action: ClickAction, id: String) {

        when (action) {
            ClickAction.LIKE -> viewModel.sendLike(id)
            ClickAction.UNLIKE -> viewModel.sendUnlike(id)
            ClickAction.OPEN_ITEM -> {
                val bundle = Bundle()
                bundle.putString(Constants.KEY_BUNDLE_PHOTO_ID, id)
                findNavController().navigate(R.id.action_homeFragment_to_openedPhotoFragment, bundle)
            }
        }
    }

    private fun setImage(imageData: ImageDataModel) {

        Glide.with(requireContext()).load(imageData.imageUrl).listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    hideImage()
                    Toast.makeText(requireContext(), R.string.error_get_photo, Toast.LENGTH_LONG).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    setImageData(imageData)
                    binding.progressCircular.visibility = View.GONE
                    return false
                }
            }
        ).into(binding.imageInteresting)
    }

    private fun setImageData(imageData: ImageDataModel) {
        val id = imageData.id
        var isLiked = imageData.isLikedByUser
        val profileImageUrl = imageData.profileImageUrl!!
        val username = imageData.username
        val login = imageData.userLogin
        val likesCount = imageData.likesCount

        with(binding.viewUserProfile) {
            setImage(profileImageUrl)
            setUsername(username)
            setUserLogin(login)
        }

        with(binding.viewLikes) {
            setLikesCount(likesCount)

            setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    if (!isLiked) {
                        isLiked = true
                        setLike()
                        viewModel.sendLike(id)
                    } else {
                        isLiked = false
                        setUnlike()
                        viewModel.sendUnlike(id)
                    }
                }
            }
        }
    }

    private fun hideImage() {
        binding.progressCircular.visibility = View.GONE
        binding.imageInteresting.visibility = View.GONE
        binding.textNew.visibility = View.GONE
        binding.textInterestingPhoto.visibility = View.GONE
        binding.viewLikes.visibility = View.GONE
        binding.viewUserProfile.visibility = View.GONE
    }
}