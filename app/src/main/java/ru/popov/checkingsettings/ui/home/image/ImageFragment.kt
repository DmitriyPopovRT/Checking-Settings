package ru.popov.checkingsettings.ui.home.image

import android.Manifest
import android.app.Activity
import android.app.RemoteAction
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.GridLayout.HORIZONTAL
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.databinding.FragmentImageBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils.haveQ
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
    private val imageViewModel: ImageListViewModel by viewModels()
    private var imagesAdapter: ImagesAdapter by autoCleared()
    private val REQUEST_IMAGE_CAPTURE = 4321
    lateinit var dateFromHomeFragment: String
    lateinit var currentPhotoPath: String

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var recoverableActionLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionResultListener()
        initRecoverableActionListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateFromHomeFragment = arguments?.let { ImageFragmentArgs.fromBundle(it) }?.date.toString()

        initList()
        bindViewModel()
        if (hasPermission().not()) {
            requestPermissions()
        }
//        viewModel.loadImages(dateFromHomeFragment, requireContext())
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(ImageFragmentDirections.actionImagesFragmentToHomeFragment())
        }
        binding.takePhoto.setOnClickListener {
            try {
                dispatchTakePictureIntent()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Нет подключения к серверу",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.grantPermissionButton.setOnClickListener {
            requestPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        imageViewModel.updatePermissionState(hasPermission())
    }

    private fun galleryAddPic(imageUri:Uri, title:String) {

        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri)
        val savedImageURL = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            title,
            "Image of $title"
        )
        Toast.makeText(requireContext(), "Picture Added to Gallery", Toast.LENGTH_SHORT).show()
    }

//    override suspend fun saveCameraImage(bitmap: Bitmap)
//    {
//        try {
//            val collection = sdk29AndUp {
//                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
//            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            val dirDest = File(
//                Environment.DIRECTORY_PICTURES,
//                context.getString(R.string.app_name)
//            )
//            val date = System.currentTimeMillis()
//            val fileName = "$date.${IMAGE_EXTENSIONS}"
//
//
//            val contentValues = ContentValues().apply {
//                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
//                put(MediaStore.MediaColumns.MIME_TYPE, "image/$IMAGE_EXTENSIONS")
//                put(MediaStore.MediaColumns.DATE_ADDED, date)
//                put(MediaStore.MediaColumns.DATE_MODIFIED, date)
//                put(MediaStore.MediaColumns.SIZE, bitmap.byteCount)
//                put(MediaStore.MediaColumns.WIDTH, bitmap.width)
//                put(MediaStore.MediaColumns.HEIGHT, bitmap.height)
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//
//                    put(MediaStore.MediaColumns.RELATIVE_PATH, "$dirDest${File.separator}")
//                    put(MediaStore.Images.Media.IS_PENDING, 1)
//                }
//            }
//
//            val imageUri = context.contentResolver.insert(collection, contentValues)
//
//
//            withContext(Dispatchers.IO) {
//
//                imageUri?.let { uri ->
//                    context.contentResolver.openOutputStream(uri, "w").use { out ->
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
//                    }
//
//                    contentValues.clear()
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)}
//                    context.contentResolver.update(uri, contentValues, null, null)
//                }
//            }
//
//
//
//        } catch (e: FileNotFoundException) {
//
//        }
//
//    }

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
                val timeStamp: String = SimpleDateFormat("HH:mm:ss_dd-MM-yyyy").format(Date())
                val title = "$timeStamp"

                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uriTest)
                val savedImageURL = MediaStore.Images.Media.insertImage(
                    requireContext().contentResolver,
                    bitmap,
                    title,
                    "Image of $title"
                )

//                val inputStream = requireContext().contentResolver.openInputStream(uriTest)
//                val byteArray = inputStream?.readBytes()
//                byteArray?.let { viewModel.savePhotoToServer(it, dateFromHomeFragment) }
//
////                viewModel.loadImages(dateFromHomeFragment)
                imagesAdapter.notifyDataSetChanged()
            }
            else -> {
                Toast.makeText(requireContext(), "Wrong request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initList() {
        imagesAdapter = ImagesAdapter(imageViewModel::deleteImage)
        with(binding.imagesList) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(true)
            adapter = imagesAdapter
        }
    }

    private fun bindViewModel() {
//        viewModel.imagesLiveData.observe(viewLifecycleOwner) { imagesAdapter.items = it }
//        viewModel.isSending.observe(viewLifecycleOwner) {
//            if (it) {
//                binding.progressBar.visibility = View.VISIBLE
//            } else {
//                binding.progressBar.visibility = View.GONE
//            }
//        }

        imageViewModel.toastLiveData.observe(viewLifecycleOwner) { toast(it) }
        imageViewModel.imagesLiveData.observe(viewLifecycleOwner) { imagesAdapter.items = it }
        imageViewModel.permissionsGrantedLiveData.observe(viewLifecycleOwner, ::updatePermissionUi)
        imageViewModel.recoverableActionLiveData.observe(viewLifecycleOwner, ::handleRecoverableAction)
    }

    private fun updatePermissionUi(isGranted: Boolean) {
        binding.grantPermissionButton.isVisible = isGranted.not()
//        binding.addImageButton.isVisible = isGranted
        binding.imagesList.isVisible = isGranted
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
                imageViewModel.permissionsGranted()
            } else {
                imageViewModel.permissionsDenied()
            }
        }
    }

    private fun initRecoverableActionListener() {
        recoverableActionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {  activityResult ->
            val isConfirmed = activityResult.resultCode == Activity.RESULT_OK
            if(isConfirmed) {
                imageViewModel.confirmDelete()
            } else {
                imageViewModel.declineDelete()
            }
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleRecoverableAction(action: RemoteAction) {
        val request = IntentSenderRequest.Builder(action.actionIntent.intentSender)
            .build()
        recoverableActionLauncher.launch(request)
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() }
        )
    }
}