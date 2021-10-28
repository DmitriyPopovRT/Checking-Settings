package ru.popov.checkingsettings.ui.home.image

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.databinding.FragmentImageBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.autoCleared
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImageFragment : Fragment(R.layout.fragment_image) {

    private val binding: FragmentImageBinding by viewBinding(FragmentImageBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private var imagesAdapter: ImagesAdapter by autoCleared()
    private val REQUEST_IMAGE_CAPTURE = 4321
    lateinit var dateFromHomeFragment: String
    lateinit var currentPhotoPath: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateFromHomeFragment = arguments?.let { ImageFragmentArgs.fromBundle(it) }?.date.toString()

        initList()
        bindViewModel()
        viewModel.loadImages(dateFromHomeFragment)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(ImageFragmentDirections.actionImagesFragmentToHomeFragment())
        }
        binding.takePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("HH:mm:ss_dd-MM-yyyy").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File("$storageDir/$timeStamp.jpg")
            .apply {
                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = absolutePath
            }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Timber.e(ex)
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
//                binding.imageView.setImageURI(currentPhotoPath.toUri())

                Timber.e("uri = ${currentPhotoPath.toUri()}")
                val uriTest = "file://$currentPhotoPath".toUri()
                val inputStream = requireContext().contentResolver.openInputStream(uriTest)
                val byteArray = inputStream?.readBytes()
                byteArray?.let { viewModel.savePhotoToServer(it, dateFromHomeFragment) }

//                viewModel.loadImages(dateFromHomeFragment)
                imagesAdapter.notifyDataSetChanged()
            }
            else -> {
                Toast.makeText(requireContext(), "Wrong request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initList() {
        imagesAdapter = ImagesAdapter(viewModel::deleteImage)
        with(binding.imagesList) {
            setHasFixedSize(true)
            adapter = imagesAdapter
        }
    }

    private fun bindViewModel() {
        viewModel.imagesLiveData.observe(viewLifecycleOwner) { imagesAdapter.items = it }
        viewModel.isSending.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}