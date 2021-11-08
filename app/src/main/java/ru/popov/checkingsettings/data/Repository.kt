package ru.popov.checkingsettings.data

import android.R.attr.password
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.squareup.moshi.Moshi
import jcifs.smb1.smb1.NtlmPasswordAuthentication
import jcifs.smb1.smb1.SmbFile
import jcifs.smb1.smb1.SmbFileInputStream
import jcifs.smb1.smb1.SmbFileOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.popov.checkingsettings.utils.JsonSettings
import ru.popov.checkingsettings.utils.LoginInformation
import ru.popov.checkingsettings.utils.Utils
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.*


class Repository(
    private val context: Context
) {

    // Собираем строку json для передачи на сервер
    suspend fun generateJsonString(): String {
        return withContext(Dispatchers.Default) {
            val moshi = Moshi.Builder()
                .add(CheckingSettingsCustomAdapter())
                .build()

            val adapter =
                moshi.adapter(CheckingSettingsCustomAdapter.CustomCheckingSettings::class.java)
                    .nonNull()

//            try {
//                Timber.d("parse = $packageJson")
            adapter.toJson(
                CheckingSettingsCustomAdapter.CustomCheckingSettings(
                    JsonSettings.packageJson,
                    JsonSettings.programJson,
                    JsonSettings.assemblyAndLabelJson,
                    JsonSettings.assemblyJson,
                    JsonSettings.speakerTestJson
                )
            )
//            } catch (e: Exception) {
//                Timber.d("parse error = ${e.message}")
//                ""
//            }
        }
    }

    // Собираем строку Упаковка
    fun convertPackageResultToJson(
        wp29: Boolean?,
        textNote: String
    ) {
        JsonSettings.packageJson = CheckingSettingsCustomAdapter.PackageWrapper(
            CheckingSettingsCustomAdapter.CheckingPackagedProduct(
                wp29,
                textNote
            )
        )
    }

    // Собираем строку Программирование и функциональная проверка
    fun convertProgramResultToJson(
        wp22Mod: Boolean?,
        wp23Mod: Boolean?,
        wp24Mod: Boolean?,
        wp25Mod: Boolean?,
        wp26Mod: Boolean?,
        wp27Mod: Boolean?,
        textNote: String,
        wp22Version: Boolean?,
        wp23Version: Boolean?,
        wp24Version: Boolean?,
        wp25Version: Boolean?,
        wp26Version: Boolean?,
        wp27Version: Boolean?,
        textNoteVersion: String
    ) {
        JsonSettings.programJson = CheckingSettingsCustomAdapter.ProgramTestWrapper(
            CheckingSettingsCustomAdapter.ModMatchesProduction(
                wp22Mod,
                wp23Mod,
                wp24Mod,
                wp25Mod,
                wp26Mod,
                wp27Mod,
                textNote
            ),
            CheckingSettingsCustomAdapter.FirmwareVersionIsCurrent(
                wp22Version,
                wp23Version,
                wp24Version,
                wp25Version,
                wp26Version,
                wp27Version,
                textNoteVersion
            )
        )
    }

    // Собираем строку Окончательная сборка и печать этикеток
    fun convertAssemblyAndLabelResultToJson(
        wp28: Boolean?,
        textNote: String
    ) {
        JsonSettings.assemblyAndLabelJson = CheckingSettingsCustomAdapter.AssemblyAndLabelWrapper(
            CheckingSettingsCustomAdapter.ScanningLabelNumberNotFound(
                wp28,
                textNote
            )
        )
    }

    // Собираем строку Сборка
    fun convertAssemblyResultToJson(
        wp12AssemblyEB: String?,
        wp13AssemblyEB: String?,
        wp14AssemblyEB: String?,
        wp15AssemblyEB: String?,
        wp16AssemblyEB: String?,
        wp17AssemblyEB: String?,
        wp18AssemblyEB: String?,
        textNoteAssemblyEB: String?,
        wp12AssemblyBip: String?,
        wp13AssemblyBip: String?,
        wp14AssemblyBip: String?,
        wp15AssemblyBip: String?,
        wp16AssemblyBip: String?,
        wp17AssemblyBip: String?,
        wp18AssemblyBip: String?,
        textNoteAssemblyBip: String?,
        wp12AssemblySpeaker: String?,
        wp13AssemblySpeaker: String?,
        wp14AssemblySpeaker: String?,
        wp15AssemblySpeaker: String?,
        wp16AssemblySpeaker: String?,
        wp17AssemblySpeaker: String?,
        wp18AssemblySpeaker: String?,
        textNoteAssemblySpeaker: String?,
        wp12AssemblySolderingTemperature: String?,
        wp13AssemblySolderingTemperature: String?,
        wp14AssemblySolderingTemperature: String?,
        wp15AssemblySolderingTemperature: String?,
        wp16AssemblySolderingTemperature: String?,
        wp17AssemblySolderingTemperature: String?,
        wp18AssemblySolderingTemperature: String?,
        textNoteAssemblySolderingTemperature: String?
    ) {
        JsonSettings.assemblyJson = CheckingSettingsCustomAdapter.AssemblyWrapper(
            CheckingSettingsCustomAdapter.AssemblyEB(
                wp12AssemblyEB,
                wp13AssemblyEB,
                wp14AssemblyEB,
                wp15AssemblyEB,
                wp16AssemblyEB,
                wp17AssemblyEB,
                wp18AssemblyEB,
                textNoteAssemblyEB
            ),
            CheckingSettingsCustomAdapter.AssemblyBIP(
                wp12AssemblyBip,
                wp13AssemblyBip,
                wp14AssemblyBip,
                wp15AssemblyBip,
                wp16AssemblyBip,
                wp17AssemblyBip,
                wp18AssemblyBip,
                textNoteAssemblyBip
            ),
            CheckingSettingsCustomAdapter.AssemblySpeaker(
                wp12AssemblySpeaker,
                wp13AssemblySpeaker,
                wp14AssemblySpeaker,
                wp15AssemblySpeaker,
                wp16AssemblySpeaker,
                wp17AssemblySpeaker,
                wp18AssemblySpeaker,
                textNoteAssemblySpeaker
            ),
            CheckingSettingsCustomAdapter.SolderingTemperature(
                wp12AssemblySolderingTemperature,
                wp13AssemblySolderingTemperature,
                wp14AssemblySolderingTemperature,
                wp15AssemblySolderingTemperature,
                wp16AssemblySolderingTemperature,
                wp17AssemblySolderingTemperature,
                wp18AssemblySolderingTemperature,
                textNoteAssemblySolderingTemperature
            )
        )
    }

    // Собираем строку Проверка динамиков
    fun convertSpeakerTestResultToJson(
        wp111: Boolean?,
        textNote: String
    ) {
        JsonSettings.speakerTestJson = CheckingSettingsCustomAdapter.SpeakerTestWrapper(
            CheckingSettingsCustomAdapter.CheckSoundSignalWhenSpeakerConnected(
                wp111,
                textNote
            )
        )
    }

    // Сохраняем строку json в файл на сервере
    suspend fun saveFileToServer(strJson: String, date: String): Boolean {
        return withContext(Dispatchers.Default) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) false

//            try {
            // Создаем архитектуру папок на сервере и получаем путь к файлу настроек
            val path = Utils.createFolderOnServer(date)

            Timber.d("fileFolder = $path")

            // Отслеживаем время загрузки данных
            val start = System.currentTimeMillis()

            // Создаем объект аутентификатор
            val auth =
                NtlmPasswordAuthentication("", LoginInformation.USER, LoginInformation.PASS)

            // Создаем файл
            val fileSettings = SmbFile(path + File.separator + LoginInformation.NAME_FILE, auth)

            // Создаем объект для потока куда мы будем писать наш файл
            val destFileName = SmbFileOutputStream(fileSettings)

            destFileName.buffered().use { fileOutputStream ->
                fileOutputStream.write(strJson.toByteArray())
            }
            Timber.d("time = ${System.currentTimeMillis() - start}")
            true
//            } catch (t: Throwable) {
//                Timber.d("t = $t")
////                file.delete()
//                false
//            }
        }
    }

    // Сохраняем строку json в файл на сервере
    suspend fun savePhotoToServer(byteArray: ByteArray, date: String): Boolean {
        return withContext(Dispatchers.Default) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) false

//            try {
            // Создаем архитектуру папок на сервере и получаем путь к файлу настроек
            val path = Utils.createFolderOnServer(date)

            Timber.d("fileFolder = $path")

            // Отслеживаем время загрузки данных
            val start = System.currentTimeMillis()
            val timeStamp: String = SimpleDateFormat("HH-mm-ss_dd-MM-yyyy").format(Date())

            // Создаем объект аутентификатор
            val auth =
                NtlmPasswordAuthentication("", LoginInformation.USER, LoginInformation.PASS)

            // Создаем файл
            val fileSettings = SmbFile(path + File.separator + timeStamp + ".jpeg", auth)

            // Создаем объект для потока куда мы будем писать наш файл
            val destFileName = SmbFileOutputStream(fileSettings)

            destFileName.buffered().use { fileOutputStream ->
                fileOutputStream.write(byteArray)
            }
            Timber.d("time = ${System.currentTimeMillis() - start}")
            true
//            } catch (t: Throwable) {
//                Timber.d("t = $t")
////                file.delete()
//                false
//            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getImages(date: String): List<Image> {
        val images = mutableListOf<Image>()
        withContext(Dispatchers.Default) {
            // Создаем архитектуру папок на сервере и получаем путь к файлу настроек
            val path = Utils.createFolderOnServer(date) + File.separator

            val auth = NtlmPasswordAuthentication(
                "", LoginInformation.USER, LoginInformation.PASS
            )

            // Ресолвим путь назначения в SmbFile
            val baseDir = SmbFile(
                path,
                auth
            )

            Timber.d("path = $path")

            val files = baseDir.listFiles()
            for (i in files.indices) {
                val fileName = files[i].name
                val extension = fileName.substring(fileName.lastIndexOf(".") + 1)
                if (extension != "jpeg") {
                    continue
                }
                val smbFile = files[i]
                val timeStamp: String = SimpleDateFormat("HH:mm:ss_dd-MM-yyyy").format(Date())
                val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val targetFile = File("$storageDir/$timeStamp.jpg")
                if (!targetFile.exists()) {
                    targetFile.createNewFile()
                }
                Files.copy(smbFile.inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING)

                images += Image(i.toLong(), targetFile.toUri(), fileName, files[i].length().toInt())
//                images += Image(i.toLong(), targetFile, fileName, files[i].length().toInt())
            }
        }
        return images
    }

    // Загружаем с сервера строку json по выбранной дате
    suspend fun downloadFileToServer(year: String, month: String, day: String): String {
        return withContext(Dispatchers.Default) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) ""

//            try {
            // Создаем объект для аутентификации на шаре
            val auth = NtlmPasswordAuthentication(
                "", LoginInformation.USER, LoginInformation.PASS
            )
            val path = LoginInformation.PATH

            // Ресолвим путь назначения в SmbFile
            val baseDir = SmbFile(
                "$path/$year/$month/$day/${LoginInformation.NAME_FILE}",
                auth
            )

            val destFileName = SmbFileInputStream(baseDir)

            var stringJsonSettings = ""
            destFileName.bufferedReader().use {
                stringJsonSettings = it.readText()
                Timber.d("str = $stringJsonSettings")
            }

            stringJsonSettings
//            } catch (t: Throwable) {
//                Timber.d("t = $t")
////                file.delete()
//                ""
//            }
        }
    }
}