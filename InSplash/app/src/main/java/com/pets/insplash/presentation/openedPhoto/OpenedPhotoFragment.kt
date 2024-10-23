package com.pets.insplash.presentation.openedPhoto

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pets.insplash.R
import com.pets.insplash.databinding.FragmentOpenedPhotoBinding
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.presentation.MainActivity
import com.pets.insplash.presentation.openedPhoto.viewModel.OpenedPhotosViewModel
import com.pets.insplash.presentation.openedPhoto.viewModel.OpenedPhotosViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
    ): View {
        _binding = FragmentOpenedPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(Constants.KEY_BUNDLE_PHOTO_ID) ?: ""

        viewLifecycleOwner.lifecycleScope.launch {
            val photo = viewModel.getPhoto(id)
            if (photo != null) {
                val photoUrl = photo.urls.raw ?: ""
                val photoLink = photo.links.html

                setImage(photo)

                binding.buttonDownload.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        downloadImage(
                            photoId = photo.id,
                            url = photoUrl,
                            name = MainActivity.FILENAME
                        )
                    }
                }

                binding.buttonShare.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        share(photoLink)
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_get_photo),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.buttonBack.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setImage(data: OnePhotoDTO?) {
        Glide.with(requireContext()).load(data?.urls?.full).listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(requireContext(), R.string.error_get_photo, Toast.LENGTH_LONG)
                        .show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    setImageData(data!!)
                    binding.progressCircular.visibility = View.GONE
                    return false
                }
            }
        ).into(binding.image)
    }

    private fun setImageData(data: OnePhotoDTO) {

        val locationString =
            if (data.location?.country != null && data.location.city != null) {
                "${data.location.city}, ${data.location.country}"
            } else {
                "-"
            }
        val tagsString = viewModel.prepareTags(data.tags)
        val madeWith = data.exif.make
        val model = data.exif.model
        val exposure = data.exif.exposure_time
        val aperture = data.exif.aperture
        val focalLength = data.exif.focal_length.toString()
        val iso = data.exif.iso.toString()
        val about = "@${data.user.username}"
        val descriptionString = data.description
        val downloadsCount = getString(R.string.download).plus("   ${data.downloads} ")
        val userProfileImage = data.user.profile_image?.medium ?: ""
        val username = data.user.name
        val login = data.user.username
        val isLiked = data.liked_by_user ?: false
        val likesCount = data.likes

        with(binding) {
            textLocation.text = locationString
            tags.text = tagsString
            valMadeWith.text = madeWith
            valModel.text = model
            valExposure.text = exposure
            valAperture.text = aperture
            valFocalLength.text = focalLength
            valIso.text = iso
            valAbout.text = about
            description.text = descriptionString
            buttonDownload.text = downloadsCount

            viewUserProfile.setImage(userProfileImage)
            viewUserProfile.setUserLogin(username)
            viewUserProfile.setUsername(login)

            viewLikes.setLikesCount(likesCount)
            viewLikes.setLikesState(isLiked)
        }

    }

    private fun share(link: String) {
        val sharedText = getString(R.string.share)
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, link)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, sharedText))
    }

    private suspend fun downloadImage(photoId: String, url: String, name: String) {
        requireActivity().requestPermissions(MainActivity.permissions, MainActivity.PERMISSION_REQUEST_CODE)

        val downloadRequestSuccessful = viewModel.sendDownloadRequest(photoId)

        if (downloadRequestSuccessful) {
            try {
                val request = viewModel.prepareRequest(url, name)
                val dm = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                dm.enqueue(request)
                Toast.makeText(requireContext(), getString(R.string.download_started), Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.error).plus(e.message), Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), R.string.error_get_photo, Toast.LENGTH_LONG).show()
        }

    }

}