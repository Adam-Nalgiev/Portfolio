package com.pets.insplash.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentHomeBinding
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.presentationModels.ClickAction
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val state = viewModel.authState(requireContext())
        if (!state)
            findNavController().navigate(R.id.action_homeFragment_to_authorizationFragment)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerHomePhotos.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            val photosData = viewModel.getPhoto()
            var isLiked = false
            if (photosData != null) {
                Glide.with(binding.imageInteresting).load(photosData.urls.full).into(binding.imageInteresting)

                Glide.with(binding.imageProfile).load(photosData.user.profile_image).into(binding.imageProfile)

                binding.likesCountText.text = photosData.likes.toString()
                binding.username.text = photosData.user.name
                binding.login.text = photosData.user.username

                binding.buttonLike.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        if (!isLiked) {
                            Glide.with(binding.buttonLike).load(R.drawable.icon_like_active).into(binding.buttonLike)
                            isLiked = true
                            viewModel.sendLike(photosData.id)
                        } else {
                            Glide.with(binding.buttonLike).load(R.drawable.icon_like_inactive).into(binding.buttonLike)
                            isLiked = false
                            viewModel.sendUnlike(photosData.id)
                        }
                    }
                }
            }


            viewModel.pagedPhotos.collect {
                adapter.submitData(it)

            }
        }

        binding.searchBar.setEndIconOnClickListener {
            binding.searchField.isVisible = !binding.searchField.isVisible
            binding.searchBar.isHintEnabled = binding.searchField.isVisible
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun onClick(action: ClickAction, id: String) {

        when(action) {
            ClickAction.LIKE -> viewModel.sendLike(id)
            ClickAction.UNLIKE -> viewModel.sendUnlike(id)
            ClickAction.OPEN_ITEM -> {
                val bundle = Bundle()
                bundle.putString(Constants.KEY_BUNDLE_PHOTO_ID, id)
                findNavController().navigate(R.id.action_homeFragment_to_openedPhotoFragment, bundle)
            }
        }
    }
}