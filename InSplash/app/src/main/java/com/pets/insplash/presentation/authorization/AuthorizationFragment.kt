package com.pets.insplash.presentation.authorization

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentAuthorizationBinding
import kotlinx.coroutines.launch

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = binding.progressBarLoading

        binding.buttonLogIn.setOnClickListener {

            viewModel.openBrowser(requireContext())

            progressBar.isVisible = true

        }

        viewModel.handleDeepLink(requireActivity().intent, requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isAuthSuccess.collect { isSuccess ->
                when (isSuccess) {

                    true -> {
                        if (requireActivity().intent != null && requireActivity().intent.action == Intent.ACTION_VIEW) {
                            findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                        }
                        progressBar.isVisible = false
                    }

                    false -> {
                        Toast.makeText(requireContext(), R.string.error_auth_process, Toast.LENGTH_LONG).show()
                        progressBar.isVisible = false
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
