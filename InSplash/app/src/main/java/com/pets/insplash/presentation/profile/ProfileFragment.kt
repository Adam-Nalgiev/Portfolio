package com.pets.insplash.presentation.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
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
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentProfileBinding
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.presentationModels.ClickAction
import com.pets.insplash.presentation.photosAdapter.PhotosAdapter
import com.pets.insplash.presentation.profile.viewModel.ProfileViewModel
import com.pets.insplash.presentation.profile.viewModel.ProfileViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private val viewModel: ProfileViewModel by viewModels { profileViewModelFactory }

    private val adapter = PhotosAdapter { clickAction, id -> onClick(clickAction, id) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.setSharedPref(context)
        viewModel.getProfile()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerPhotos.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collect {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileFlow.collect {
                if (it != null) {
                    with(binding) {

                        setImage(
                            it.profile_image.large,
                            it.first_name,
                            it.last_name,
                            "@${it.username}"
                        )

                        textLocation.text =
                            if (it.location != null)
                                "${it.location.city}, ${it.location.city}"
                            else
                                "-"

                        textDescription.text = it.bio ?: ""
                        textEmail.text = it.email ?: "-"
                        textDownloads.text = it.download.toString()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect{
                binding.progressCircular.isVisible = it.source.refresh is LoadState.Loading
            }
        }

        binding.buttonExit.setOnClickListener {
            binding.viewConfirmation.visibility = View.VISIBLE
        }

        binding.buttonLogout.setOnClickListener {
            viewModel.exit(requireContext())
            findNavController().navigate(R.id.action_profileFragment_to_authorizationFragment)
        }

        binding.buttonCancel.setOnClickListener {
            binding.viewConfirmation.visibility = View.GONE
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

                findNavController().navigate(
                    R.id.action_homeFragment_to_openedPhotoFragment,
                    bundle
                )
            }
        }
    }

    private fun setImage(uri: String, firstName: String, lastName: String, login: String) {

        Glide.with(requireContext()).load(uri).listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressCircular.visibility = View.GONE
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
                    setData(firstName, lastName, login)
                    binding.progressCircular.visibility = View.GONE
                    return false
                }
            }
        ).circleCrop().into(binding.imageProfile)
    }

    private fun setData(firstName: String, lastName: String, login: String) {
        with(binding) {
            textFirstName.text = firstName
            textLastName.text = lastName
            textLogin.text = login
        }
    }

}