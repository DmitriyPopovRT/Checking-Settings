package ru.popov.checkingsettings.data

import android.net.Uri
import java.io.File

data class Image(
    val id: Long,
    val uri: Uri,
//    val file: File,
    val name: String,
    val size: Int
)
