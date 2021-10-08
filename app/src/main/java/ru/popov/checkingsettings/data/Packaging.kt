package ru.popov.checkingsettings.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Packaging(
    val wp29: Boolean?,
    val note: String?
)
