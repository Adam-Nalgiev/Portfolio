package com.pets.insplash.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentCollectionsBinding
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.presentation.collections.adapter.CollectionsAdapter
import com.pets.insplash.presentation.collections.viewModel.CollectionsViewModel
import com.pets.insplash.presentation.collections.viewModel.CollectionsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CollectionsFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    private val adapter = CollectionsAdapter { id -> onClick(id) }

    @Inject
    lateinit var collectionViewModelFactory: CollectionsViewModelFactory

    private val viewModel: CollectionsViewModel by viewModels { collectionViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collect {
                adapter.submitData(it)
            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onClick(id: String) {
        val bundle = Bundle()
        bundle.putString(Constants.KEY_BUNDLE_COLLECTION_ID, id)
        findNavController().navigate(
            R.id.action_collectionsFragment_to_openedCollectionFragment,
            bundle
        )
    }
}