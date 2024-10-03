package com.pets.insplash.presentation.openedPhoto

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentOpenedPhotoBinding
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.presentation.openedPhoto.viewModel.OpenedPhotosViewModel
import com.pets.insplash.presentation.openedPhoto.viewModel.OpenedPhotosViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class OpenedPhotoFragment : Fragment() {

    private var _binding: FragmentOpenedPhotoBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: OpenedPhotosViewModelFactory

    private val viewModel: OpenedPhotosViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenedPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(Constants.KEY_BUNDLE_PHOTO_ID) ?: ""
        Log.d("ID", id)


        viewLifecycleOwner.lifecycleScope.launch {
            val photo = viewModel.getPhoto(id)
            Log.d("DATA", "$photo")

            if (photo != null) {
                Glide.with(requireContext()).load(photo.urls.full).optionalCenterCrop().into(binding.image)
                Glide.with(requireContext()).load(photo.user.profile_image).into(binding.imageProfile)

                binding.username.text = photo.user.name
                binding.login.text = photo.user.username
                binding.likes.text = photo.likes.toString()
                if (photo.location?.city != null && photo.location.country != null) {
                    binding.location.text = "${photo.location.city}, ${photo.location.country}"
                } else {
                    binding.location.text = "-"
                }
                val tags = mutableListOf<String>()
                photo.tags.onEach {
                    tags.add("#${it.title}")
                }
                binding.tags.text = tags.toString().removePrefix("[").removeSuffix("]")
                binding.valMadeWith.text = photo.exif.make
                binding.valModel.text = photo.exif.model
                binding.valExposure.text = photo.exif.exposure_time
                binding.valAperture.text = photo.exif.aperture
                binding.valFocalLength.text = photo.exif.focal_length.toString()
                binding.valIso.text = photo.exif.iso.toString()
                binding.valAbout.text = "@${photo.user.username}"
                binding.description.text = photo.description
                binding.downloads.text = photo.downloads.toString()
            } else {
                Toast.makeText(requireContext(), R.string.error_get_photo, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}