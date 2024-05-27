package ru.popov.checkingsettings.ui.image

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.databinding.FragmentImageFullscreenBinding
import timber.log.Timber

class ImageFullscreenFragment : Fragment(R.layout.fragment_image_fullscreen) {

    private val binding: FragmentImageFullscreenBinding by viewBinding(
        FragmentImageFullscreenBinding::bind
    )
    private lateinit var uriImage: String
    private lateinit var date: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uriImage = arguments?.let { ImageFullscreenFragmentArgs.fromBundle(it) }?.imageId.toString()
        date = arguments?.let { ImageFullscreenFragmentArgs.fromBundle(it) }?.date.toString()

        Timber.d("uri = $uriImage")
        binding.image.setImageURI(uriImage.toUri())

        binding.close.setOnClickListener {
            findNavController().popBackStack()
//            findNavController().navigate(
//                ImageFullscreenFragmentDirections.actionImageFullscreenFragmentToImagesFragment(date)
//            )
        }
    }
}