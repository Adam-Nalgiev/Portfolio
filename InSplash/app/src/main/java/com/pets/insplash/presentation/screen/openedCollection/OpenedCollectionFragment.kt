package com.pets.insplash.presentation.screen.openedCollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentOpenedCollectionBinding
import com.pets.insplash.presentation.models.ClickAction
import com.pets.insplash.presentation.photosAdapter.PhotosAdapter
import com.pets.insplash.presentation.screen.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OpenedCollectionFragment : Fragment() {

    private var _binding: FragmentOpenedCollectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OpenedCollectionViewModel by viewModels()

    private val adapter = PhotosAdapter { action, id -> onClick(action, id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenedCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(MainActivity.KEY_BUNDLE_COLLECTION_ID) ?: ""
        viewModel.setId(id)

        binding.recyclerCollectionsPhotos.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.photos.collect{
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect{
                binding.progressCircular.isVisible = it.source.refresh is LoadState.Loading
            }
        }

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
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
                bundle.putString(MainActivity.KEY_BUNDLE_PHOTO_ID, id)
                findNavController().navigate(
                    R.id.action_openedCollectionFragment_to_openedPhotoFragment,
                    bundle
                )
            }
        }
    }
}