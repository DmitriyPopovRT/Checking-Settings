package ru.popov.checkingsettings.ui.image

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.Image
import ru.popov.checkingsettings.databinding.FragmentImageBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils
import ru.popov.checkingsettings.utils.Utils.toast
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
    private lateinit var dateFromHomeFragment: String
    private lateinit var currentPhotoPath: String

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    private var listImagesSave: List<Image>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        // Запрашиваем разрешения на доступ к камере и файловой системе
        initPermissionResultListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        // Получаем аргументы из предыдущего фрагмента
        dateFromHomeFragment = arguments?.let { ImageFragmentArgs.fromBundle(it) }?.date.toString()

        initList()
        bindViewModel()
        if (hasPermission().not()) {
            requestPermissions()
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(ImageFragmentDirections.actionImagesFragmentToHomeFragment())
        }
        binding.takePhoto.setOnClickListener {
            try {
                dispatchTakePictureIntent()
            } catch (e: Exception) {
                toast(R.string.no_connection)
            }
        }
        binding.grantPermissionButton.setOnClickListener {
            requestPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        if (listImagesSave != null) {
            imagesAdapter.items = listImagesSave
            if (listImagesSave!!.isEmpty()) binding.listEmpty.visibility = View.VISIBLE
            else binding.listEmpty.visibility = View.GONE
        } else
            viewModel.updatePermissionState(hasPermission(), dateFromHomeFragment)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("HH:mm:ss_dd-MM-yyyy").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File("$storageDir/$timeStamp.jpg")
            .apply {
                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = absolutePath
            }
    }

    // Ловим из интента фото
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

    // Запускаем камеру и делаем фото
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
//                binding.imageView.setImageURI(currentPhotoPath.toUri())

                Timber.e("uri = ${currentPhotoPath.toUri()}")
                val uriTest = "file://$currentPhotoPath".toUri()

                try {
                    requireContext().contentResolver.openInputStream(uriTest).use { inputStream ->
                        inputStream?.readBytes()?.let { byteArray ->
                            viewModel.savePhotoToServer(byteArray, dateFromHomeFragment)
                        }
                    }
//                    val byteArray = inputStream?.readBytes()
//                    byteArray?.let { viewModel.savePhotoToServer(it, dateFromHomeFragment) }
//                    inputStream?.close()
                } catch (e: Exception) {
                    Timber.e(e)
                }
                imagesAdapter.notifyDataSetChanged()
            }

            else -> {
                toast("Wrong request code")
            }
        }
    }

    // Ловим нажатие и открываем фото на весь экран
    private fun onClickPhoto(uri: String) {
        findNavController().navigate(
            ImageFragmentDirections.actionImagesFragmentToImageFullscreenFragment(
                uri,
                dateFromHomeFragment
            )
        )
    }

    private fun initList() {
        imagesAdapter = ImagesAdapter(
            { deleteConfirmation(it) },
            { onClickPhoto(it) }
        )
        with(binding.imagesList) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(true)
            adapter = imagesAdapter
        }
    }

    private fun bindViewModel() {
        viewModel.imagesLiveData.observe(viewLifecycleOwner) {
            listImagesSave = it
            imagesAdapter.items = it
            if (it.isEmpty()) binding.listEmpty.visibility = View.VISIBLE
            else binding.listEmpty.visibility = View.GONE
        }
        viewModel.isSendingImages.observe(viewLifecycleOwner) {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }
        viewModel.isSendingPhoto.observe(viewLifecycleOwner) {
            if (it) viewModel.loadImages(dateFromHomeFragment)
        }
        viewModel.permissionsGrantedLiveData.observe(viewLifecycleOwner, ::updatePermissionUi)
        viewModel.isDeletePhoto.observe(viewLifecycleOwner) {
            viewModel.loadImages(dateFromHomeFragment)
        }
    }

    private fun updatePermissionUi(isGranted: Boolean) {
        binding.grantPermissionButton.isVisible = isGranted.not()
        binding.imagesList.isVisible = isGranted
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView")
//        viewModel.deleteAllFilesFolder()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        viewModel.deleteAllFilesFolder()
    }

    private fun hasPermission(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initPermissionResultListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            if (permissionToGrantedMap.values.all { it }) {
                viewModel.permissionsGranted(dateFromHomeFragment)
            } else {
                viewModel.permissionsDenied()
            }
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
    }

    // Диалог подтверждения удаления
    private fun deleteConfirmation(name: String) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.delete_confirm_dialog, null)
        val positiveButton = dialogView.findViewById<Button>(R.id.positiveButton)
        val negativeButton = dialogView.findViewById<Button>(R.id.negativeButton)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()

        positiveButton.setOnClickListener {
            viewModel.deleteImage(dateFromHomeFragment, name)
            dialog.dismiss()
        }

        negativeButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 4321
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { Utils.haveQ().not() }
        )
    }
}