package ru.popov.checkingsettings.data

import java.io.File

data class Image(
    val id: Long,
    val file: File,
    val name: String,
    val size: Int
)
