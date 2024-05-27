package ru.popov.checkingsettings.utils

import android.content.Context
import android.widget.Toast
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException

// Парсинг файла настроек
// Файл хранится во внутренних файлах ПО
class SettingsParse(val context: Context) {

    private val filePath = context.getFileStreamPath("settings.jsonc")?.path

    var serverName = "10.50.3.3"
    var shareName = "Public"
    var path = "ОТК/Проверка настроек"
    var login = "OTK_rw"
    var password = "Undertale777!"
//    var password = "ZYZbnrGvOOxuQMJxR1B1"

    init {
        try {
            val file = File(filePath)
            val str = StringBuilder()
            file.forEachLine {
                str.append(it.substringBeforeLast("//").trim())
            }

            val settings = JSONObject(str.toString())
            if (settings.has("serverName")) serverName = settings.getString("serverName")
            if (settings.has("shareName")) shareName = settings.getString("shareName")
            if (settings.has("path")) path = settings.getString("path")
            if (settings.has("user")) login = settings.getString("user")
            if (settings.has("password")) password = settings.getString("password")
        } catch (e: FileNotFoundException) {
            Toast.makeText(
                context,
                "Файл настроек не найден",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}