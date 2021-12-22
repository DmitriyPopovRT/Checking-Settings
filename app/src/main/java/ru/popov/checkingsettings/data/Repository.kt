package ru.popov.checkingsettings.data

import android.content.Context
import android.os.Environment
import com.squareup.moshi.Moshi
import jcifs.smb1.smb1.NtlmPasswordAuthentication
import jcifs.smb1.smb1.SmbFile
import jcifs.smb1.smb1.SmbFileInputStream
import jcifs.smb1.smb1.SmbFileOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter.ValueAndCalibration
import ru.popov.checkingsettings.utils.JsonSettings
import ru.popov.checkingsettings.utils.LoginInformation
import ru.popov.checkingsettings.utils.Utils
import timber.log.Timber
import java.io.File
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
//                .add(CheckingSettingsCustomAdapter.ColorAdapter2())
//                .add(CheckingSettingsCustomAdapter.ColorAdapter())
                .build()

            val adapter =
                moshi.adapter(CheckingSettingsCustomAdapter.CustomCheckingSettings::class.java)
                    .nonNull()

            adapter.toJson(
                CheckingSettingsCustomAdapter.CustomCheckingSettings(
                    JsonSettings.packageJson,
                    JsonSettings.programJson,
                    JsonSettings.assemblyAndLabelJson,
                    JsonSettings.assemblyJson,
                    JsonSettings.speakerTestJson
                )
            )
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
        wp11AssemblyEB: ValueAndCalibration?,
        wp12AssemblyEB: ValueAndCalibration?,
        wp13AssemblyEB: ValueAndCalibration?,
        wp14AssemblyEB: ValueAndCalibration?,
        wp15AssemblyEB: ValueAndCalibration?,
        wp16AssemblyEB: ValueAndCalibration?,
        wp17AssemblyEB: ValueAndCalibration?,
        wp18AssemblyEB: ValueAndCalibration?,
        textNoteAssemblyEB: String?,
        wp11AssemblyBip: ValueAndCalibration?,
        wp12AssemblyBip: ValueAndCalibration?,
        wp13AssemblyBip: ValueAndCalibration?,
        wp14AssemblyBip: ValueAndCalibration?,
        wp15AssemblyBip: ValueAndCalibration?,
        wp16AssemblyBip: ValueAndCalibration?,
        wp17AssemblyBip: ValueAndCalibration?,
        wp18AssemblyBip: ValueAndCalibration?,
        textNoteAssemblyBip: String?,
        wp11AssemblySpeaker: ValueAndCalibration?,
        wp12AssemblySpeaker: ValueAndCalibration?,
        wp13AssemblySpeaker: ValueAndCalibration?,
        wp14AssemblySpeaker: ValueAndCalibration?,
        wp15AssemblySpeaker: ValueAndCalibration?,
        wp16AssemblySpeaker: ValueAndCalibration?,
        wp17AssemblySpeaker: ValueAndCalibration?,
        wp18AssemblySpeaker: ValueAndCalibration?,
        textNoteAssemblySpeaker: String?,
        wp11AssemblySolderingTemperature: ValueAndCalibration?,
        wp12AssemblySolderingTemperature: ValueAndCalibration?,
        wp13AssemblySolderingTemperature: ValueAndCalibration?,
        wp14AssemblySolderingTemperature: ValueAndCalibration?,
        wp15AssemblySolderingTemperature: ValueAndCalibration?,
        wp16AssemblySolderingTemperature: ValueAndCalibration?,
        wp17AssemblySolderingTemperature: ValueAndCalibration?,
        wp18AssemblySolderingTemperature: ValueAndCalibration?,
        textNoteAssemblySolderingTemperature: String?,
        wp23AssemblyFixing: ValueAndCalibration?,
        wp24AssemblyFixing: ValueAndCalibration?,
        wp25AssemblyFixing: ValueAndCalibration?,
        wp26AssemblyFixing: ValueAndCalibration?,
        wp27AssemblyFixing: ValueAndCalibration?,
        wp28AssemblyFixing: ValueAndCalibration?,
        textNoteAssemblyFixing: String?
    ) {
        JsonSettings.assemblyJson = CheckingSettingsCustomAdapter.AssemblyWrapper(
            CheckingSettingsCustomAdapter.AssemblyEB(
                wp11AssemblyEB,
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
                wp11AssemblyBip,
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
                wp11AssemblySpeaker,
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
                wp11AssemblySolderingTemperature,
                wp12AssemblySolderingTemperature,
                wp13AssemblySolderingTemperature,
                wp14AssemblySolderingTemperature,
                wp15AssemblySolderingTemperature,
                wp16AssemblySolderingTemperature,
                wp17AssemblySolderingTemperature,
                wp18AssemblySolderingTemperature,
                textNoteAssemblySolderingTemperature
            ),
            CheckingSettingsCustomAdapter.AssemblyFixing(
                wp23AssemblyFixing,
                wp24AssemblyFixing,
                wp25AssemblyFixing,
                wp26AssemblyFixing,
                wp27AssemblyFixing,
                wp28AssemblyFixing,
                textNoteAssemblyFixing
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
        }
    }

    // Сохраняем фото на сервере
    suspend fun savePhotoToServer(byteArray: ByteArray, date: String): Boolean {
        return withContext(Dispatchers.Default) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) false

            // Создаем архитектуру папок на сервере и получаем путь к файлу настроек
            val path = Utils.createFolderOnServer(date)

            Timber.d("fileFolder = $path")

            // Отслеживаем время загрузки данных
            val start = System.currentTimeMillis()
            val timeStamp: String = SimpleDateFormat("HH-mm-ss dd.MM.yyyy").format(Date())

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
        }
    }

    // Получаем список фото с сервера
    suspend fun getImages(date: String): List<Image> {
        val images = mutableListOf<Image>()
        withContext(Dispatchers.Default) {
            // Создаем архитектуру папок на сервере и получаем путь к папке
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
                val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val targetFile = File("$storageDir/$fileName")
                if (!targetFile.exists()) {
                    targetFile.createNewFile()
                }

                val smbFileInputStream = SmbFileInputStream(smbFile)
                smbFileInputStream.use {
                    Files.copy(
                        it,
                        targetFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }

//                smbFile.inputStream.use { inputStream ->
//                    Files.copy(
//                        inputStream,
//                        targetFile.toPath(),
//                        StandardCopyOption.REPLACE_EXISTING
//                    )
//                }

//                smbFile.inputStream.close()
//                smbFile.outputStream.close()

                images += Image(i.toLong(), targetFile, fileName, files[i].length().toInt())
            }
        }
        return images
    }

    // Загружаем с сервера строку json по выбранной дате
    suspend fun downloadFileToServer(year: String, month: String, day: String): String {
//        return withContext(Dispatchers.Default) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) ""

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
//            Timber.d("str = $stringJsonSettings")
        }

        return stringJsonSettings
//        }
    }

    private fun stringJsonToCustomAdapter(strJson: String): CheckingSettingsCustomAdapter.CustomCheckingSettings? {
        val moshi = Moshi.Builder()
            .add(CheckingSettingsCustomAdapter())
//            .add(CheckingSettingsCustomAdapter.ColorAdapter2())
//            .add(CheckingSettingsCustomAdapter.ColorAdapter())
            .build()

        val adapter =
            moshi.adapter(CheckingSettingsCustomAdapter.CustomCheckingSettings::class.java)
                .nonNull()

//        return try {
        return adapter.fromJson(strJson)
        //            Timber.d("parse t = $t")
//        } catch (e: Exception) {
//            Timber.d("parse error = ${e.message}")
//            null
//        }
    }

    suspend fun download(
        year: String,
        month: String,
        day: String
    ): CheckingSettingsCustomAdapter.CustomCheckingSettings? {
        return withContext(Dispatchers.IO) {
            var strJson = ""
//            Timber.d("$day isExistFile = ${isExistFile(year, month, day)}")
            if (isExistFile(year, month, day)) {
                strJson = downloadFileToServer(year, month, day)
            }

//            Timber.d("$day strJson = ${strJson}")
            if (strJson.isNotEmpty()) {

//                Timber.e("$day Adapter = ${stringJsonToCustomAdapter(strJson)}")
                stringJsonToCustomAdapter(strJson)
            } else
                null
        }
    }

    suspend fun downloadStatistics(
        year: Int,
        month: Int,
        day: Int,
        operation: String,
        workPlace: String
    ): HashMap<Date, ValueAndCalibration> {
        return withContext(Dispatchers.IO) {
            val list = hashMapOf<Date, ValueAndCalibration>()
            val adapterAssembly =
                download(year.toString(), month.toString(), day.toString())?.assembly

            val currentDate = GregorianCalendar(year, month - 1, day).time
            val listAssembly = hashMapOf<Date, CheckingSettingsCustomAdapter.AssemblyWrapper?>()

            listAssembly.put(currentDate, adapterAssembly)
            listAssembly.values.forEach { assemblyWrapper ->
                when (operation) {
                    context.resources.getString(R.string.assembly_eb) -> {
                        val assemblyEB = assemblyWrapper?.assemblyEB
                        when (workPlace) {
                            "1.1" -> {
                                assemblyEB?.wp11?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.2" -> {
                                assemblyEB?.wp12?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.3" -> {
                                assemblyEB?.wp13?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.4" -> {
                                assemblyEB?.wp14?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.5" -> {
                                assemblyEB?.wp15?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.6" -> {
                                assemblyEB?.wp16?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.7" -> {
                                assemblyEB?.wp17?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.8" -> {
                                assemblyEB?.wp18?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                        }
                    }
                    context.resources.getString(R.string.assembly_bip) -> {
                        val assemblyBIP = assemblyWrapper?.assemblyBIP
                        when (workPlace) {
                            "1.1" -> {
                                assemblyBIP?.wp11?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.2" -> {
                                assemblyBIP?.wp12?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.3" -> {
                                assemblyBIP?.wp13?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.4" -> {
                                assemblyBIP?.wp14?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.5" -> {
                                assemblyBIP?.wp15?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.6" -> {
                                assemblyBIP?.wp16?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.7" -> {
                                assemblyBIP?.wp17?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.8" -> {
                                assemblyBIP?.wp18?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                        }
                    }
                    context.resources.getString(R.string.assembly_speaker) -> {
                        val assemblySpeaker = assemblyWrapper?.assemblySpeaker
                        when (workPlace) {
                            "1.1" -> {
                                assemblySpeaker?.wp11?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.2" -> {
                                assemblySpeaker?.wp12?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.3" -> {
                                assemblySpeaker?.wp13?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.4" -> {
                                assemblySpeaker?.wp14?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.5" -> {
                                assemblySpeaker?.wp15?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.6" -> {
                                assemblySpeaker?.wp16?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.7" -> {
                                assemblySpeaker?.wp17?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.8" -> {
                                assemblySpeaker?.wp18?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                        }
                    }
                    context.resources.getString(R.string.assembly_temp) -> {
                        val solderingTemperature = assemblyWrapper?.solderingTemperature
                        when (workPlace) {
                            "1.1" -> {
                                solderingTemperature?.wp11?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.2" -> {
                                solderingTemperature?.wp12?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.3" -> {
                                solderingTemperature?.wp13?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.4" -> {
                                solderingTemperature?.wp14?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.5" -> {
                                solderingTemperature?.wp15?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.6" -> {
                                solderingTemperature?.wp16?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.7" -> {
                                solderingTemperature?.wp17?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "1.8" -> {
                                solderingTemperature?.wp18?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                        }
                    }
                    context.resources.getString(R.string.assembly_fixation) -> {
                        val assemblyFixing = assemblyWrapper?.assemblyFixing
                        when (workPlace) {
                            "2.3" -> {
                                assemblyFixing?.wp23?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "2.4" -> {
                                assemblyFixing?.wp24?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "2.5" -> {
                                assemblyFixing?.wp25?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "2.6" -> {
                                assemblyFixing?.wp26?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "2.7" -> {
                                assemblyFixing?.wp27?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                            "2.8" -> {
                                assemblyFixing?.wp28?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                        }
                    }
                }
            }

//            Timber.d("$day list = ${list}")
            return@withContext list
        }
    }

    // Удаляем все файлы из временной папки с картинками
    // Чтобы отобразить файлы с сервера, нужно создать их копии в памяти телефона
    suspend fun deleteAllFilesFolder() {
        withContext(Dispatchers.Default) {
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            for (myFile in File(storageDir?.path).listFiles()) {
                if (myFile.isFile) {
                    myFile.delete()
                }
            }
        }
    }

    // Удаление фото
    suspend fun deleteImage(date: String, name: String): Boolean {
        return withContext(Dispatchers.Default) {
            val path = Utils.createFolderOnServer(date)
            // Создаем объект аутентификатор
            val auth =
                NtlmPasswordAuthentication("", LoginInformation.USER, LoginInformation.PASS)
            // Ищем файл
            val file = SmbFile(
                path + File.separator + name, auth,
                SmbFile.FILE_SHARE_READ or SmbFile.FILE_SHARE_WRITE or SmbFile.FILE_SHARE_DELETE
            )
            Timber.d("delete file = ${path + File.separator + name}")
            file.delete()
            true
        }
    }

    // Проверяет существует ли файл настроек
    suspend fun isExistFile(year: String, month: String, day: String): Boolean {
        return withContext(Dispatchers.Default) {
            // Создаем объект аутентификатор
            val auth =
                NtlmPasswordAuthentication("", LoginInformation.USER, LoginInformation.PASS)

            // Ресолвим путь назначения в SmbFile
            val baseDir = SmbFile(
                "${LoginInformation.PATH}/$year/$month/$day/${LoginInformation.NAME_FILE}",
                auth
            )
            baseDir.exists()
        }
    }
}