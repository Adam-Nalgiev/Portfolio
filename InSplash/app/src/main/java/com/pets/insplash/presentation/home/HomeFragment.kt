package com.pets.insplash.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentHomeBinding
import com.pets.insplash.entity.constants.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

 //   private val adapter:HomeAdapter = HomeAdapter { id -> if (id != null) openPhoto(id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val state = viewModel.authState(requireContext())
        if (!state){
            findNavController().navigate(R.id.action_homeFragment_to_authorizationFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* binding.recyclerHomePhotos.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagedPhotos.collect{
                adapter.submitData(it)
            }
        }

        binding.searchBar.setEndIconOnClickListener {
            binding.searchField.isVisible = !binding.searchField.isVisible
            binding.searchBar.isHintEnabled = binding.searchField.isVisible
        }
*/

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun openPhoto(id: String) {
        Bundle().putString(Constants.KEY_BUNDLE_PHOTO_ID, id)
        findNavController().navigate(R.id.action_homeFragment_to_openedPhotoFragment)
    }
}