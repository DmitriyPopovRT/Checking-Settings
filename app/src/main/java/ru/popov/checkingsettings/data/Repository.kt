package ru.popov.checkingsettings.data

import android.content.Context
import android.os.Environment
import ch.swaechter.smbjwrapper.SmbConnection
import ch.swaechter.smbjwrapper.SmbDirectory
import ch.swaechter.smbjwrapper.SmbFile
import com.hierynomus.smbj.auth.AuthenticationContext
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter.ValueAndCalibration
import ru.popov.checkingsettings.utils.JsonSettings
import ru.popov.checkingsettings.utils.LoginInformation
import ru.popov.checkingsettings.utils.Utils
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.Date
import java.util.GregorianCalendar

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

            adapter.toJson(
                CheckingSettingsCustomAdapter.CustomCheckingSettings(
                    JsonSettings.packageJson,
                    JsonSettings.programJson,
                    JsonSettings.assemblyAndLabelJson,
                    JsonSettings.assemblyJson,
                    JsonSettings.speakerTestJson,
                    JsonSettings.bipTestJson,
                    JsonSettings.testConnectorJson,
                    JsonSettings.outageJson,
                    JsonSettings.exitDeviceJson
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
        wp33: Boolean?,
        wp34: Boolean?,
        wp36: Boolean?,
        wp37: Boolean?,
        textNote: String
    ) {
        JsonSettings.assemblyAndLabelJson = CheckingSettingsCustomAdapter.AssemblyAndLabelWrapper(
            CheckingSettingsCustomAdapter.ScanningLabelNumberNotFound(
                wp33,
                wp34,
                wp36,
                wp37,
                textNote
            )
        )
    }

    // Собираем строку Сборка
    fun convertAssemblyResultToJson(
        wp07AssemblyEB: ValueAndCalibration?,
        wp08AssemblyEB: ValueAndCalibration?,
        wp09AssemblyEB: ValueAndCalibration?,
        wp10AssemblyEB: ValueAndCalibration?,
        textNoteAssemblyEB: String?,
        wp11AssemblyBip: ValueAndCalibration?,
        wp12AssemblyBip: ValueAndCalibration?,
        wp13AssemblyBip: ValueAndCalibration?,
        wp14AssemblyBip: ValueAndCalibration?,
        wp15AssemblyBip: ValueAndCalibration?,
        wp16AssemblyBip: ValueAndCalibration?,
        textNoteAssemblyBip: String?,
        wp01AssemblySolderingTemperature: ValueAndCalibration?,
        wp02AssemblySolderingTemperature: ValueAndCalibration?,
        wp03AssemblySolderingTemperature: ValueAndCalibration?,
        wp04AssemblySolderingTemperature: ValueAndCalibration?,
        wp05AssemblySolderingTemperature: ValueAndCalibration?,
        wp19AssemblySolderingTemperature: ValueAndCalibration?,
        wp20AssemblySolderingTemperature: ValueAndCalibration?,
        wp21AssemblySolderingTemperature: ValueAndCalibration?,
        wp22AssemblySolderingTemperature: ValueAndCalibration?,
        wp23AssemblySolderingTemperature: ValueAndCalibration?,
        wp24AssemblySolderingTemperature: ValueAndCalibration?,
        textNoteAssemblySolderingTemperature: String?,
    ) {
        JsonSettings.assemblyJson = CheckingSettingsCustomAdapter.AssemblyWrapper(
            CheckingSettingsCustomAdapter.AssemblyEB(
                wp07AssemblyEB,
                wp08AssemblyEB,
                wp09AssemblyEB,
                wp10AssemblyEB,
                textNoteAssemblyEB
            ),
            CheckingSettingsCustomAdapter.AssemblyBIP(
                wp11AssemblyBip,
                wp12AssemblyBip,
                wp13AssemblyBip,
                wp14AssemblyBip,
                wp15AssemblyBip,
                wp16AssemblyBip,
                textNoteAssemblyBip
            ),
            CheckingSettingsCustomAdapter.SolderingTemperature(
                wp01AssemblySolderingTemperature,
                wp02AssemblySolderingTemperature,
                wp03AssemblySolderingTemperature,
                wp04AssemblySolderingTemperature,
                wp05AssemblySolderingTemperature,
                wp19AssemblySolderingTemperature,
                wp20AssemblySolderingTemperature,
                wp21AssemblySolderingTemperature,
                wp22AssemblySolderingTemperature,
                wp23AssemblySolderingTemperature,
                wp24AssemblySolderingTemperature,
                textNoteAssemblySolderingTemperature
            )
        )
    }

    // Собираем строку Проверка динамиков
    fun convertSpeakerTestResultToJson(
        wp25: Boolean?,
        wp26: Boolean?,
        wpGold25: Boolean?,
        wpGold26: Boolean?,
        textNote: String,
        textNoteGold: String
    ) {
        JsonSettings.speakerTestJson = CheckingSettingsCustomAdapter.SpeakerTestWrapper(
            CheckingSettingsCustomAdapter.CheckSoundSignalWhenSpeakerConnected(
                wp25,
                wp26,
                wpGold25,
                wpGold26,
                textNote,
                textNoteGold
            )
        )
    }

    // Собираем строку Проверка БИП
    fun convertBipTestResultToJson(
        wp17: Boolean?,
        wp18: Boolean?,
        textNote: String
    ) {
        JsonSettings.bipTestJson = CheckingSettingsCustomAdapter.BipTestWrapper(
            CheckingSettingsCustomAdapter.GoldBipTest(
                wp17,
                wp18,
                textNote
            )
        )
    }

    // Собираем строку Проверка соединителя
    fun convertConnectorTestResultToJson(
        wp35: Boolean?,
        textNote: String
    ) {
        JsonSettings.testConnectorJson = CheckingSettingsCustomAdapter.TestConnectorWrapper(
            CheckingSettingsCustomAdapter.CheckingGold(
                wp35,
                textNote
            )
        )
    }

    // Собираем строку Простой
    fun convertOutageResultToJson(
        outage: Boolean?,
        textNote: String
    ) {
        JsonSettings.outageJson = CheckingSettingsCustomAdapter.OutageWrapper(
            CheckingSettingsCustomAdapter.CheckingOutage(
                outage,
                textNote
            )
        )
    }

    // Собираем строку Проверка соединителя
    fun convertExitDeviceResultToJson(
        exitDevice: Boolean?,
        textNoteExitDevice: String
    ) {
        JsonSettings.exitDeviceJson = CheckingSettingsCustomAdapter.ExitDeviceWrapper(
            CheckingSettingsCustomAdapter.ExitDevice(
                exitDevice,
                textNoteExitDevice
            )
        )
    }

    // Сохраняем строку json в файл на сервере
    suspend fun saveFileToServer(strJson: String, date: String): Boolean {
        return withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) false

            // Создаем архитектуру папок на сервере и получаем путь к файлу настроек
            val path = Utils.createFolderOnServer(date)
            Timber.d("fileFolder = $path")

            // Отслеживаем время загрузки данных
            val start = System.currentTimeMillis()

            val authenticationContext =
                AuthenticationContext(
                    LoginInformation.USER,
                    LoginInformation.PASS.toCharArray(),
                    ""
                )
//            val clientConfig = SmbConfig.builder()
//                .withDfsEnabled(true)
//                .withSecurityProvider(BCSecurityProvider())
//                .withDialects(SMB2Dialect.SMB_3_1_1)
//                .withMultiProtocolNegotiate(true)
//                .withSigningRequired(true)
//                .withEncryptData(true)
//                .build()

            // Ресолвим путь назначения в SmbFile
            val smbConnect = SmbConnection(
                LoginInformation.SERVERNAME,
                LoginInformation.SHARENAME,
                authenticationContext
            )
            val smbFile = SmbFile(smbConnect, path + File.separator + LoginInformation.NAME_FILE)

            var destFileName: OutputStream? = null
            try {
                smbFile.deleteFile()
                destFileName = smbFile.outputStream
                destFileName.buffered().use { fileOutputStream ->
                    fileOutputStream.write(strJson.toByteArray())
                }
            } catch (ex: IOException) {
                println(ex.message)
            } finally {
                try {
                    destFileName?.close()
                } catch (ex: IOException) {
                    println(ex.message)
                }
            }
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
            val authenticationContext = AuthenticationContext(
                LoginInformation.USER,
                LoginInformation.PASS.toCharArray(),
                ""
            )
            SmbConnection(
                LoginInformation.SERVERNAME,
                LoginInformation.SHARENAME,
                authenticationContext
            ).use { smbConnection ->
                // Создаем файл
                val filePhoto =
                    SmbFile(smbConnection, path + File.separator + timeStamp + ".jpeg")

                // Создаем объект для потока куда мы будем писать наш файл
                var destFileName: OutputStream? = null
                try {
                    destFileName = filePhoto.outputStream
                    destFileName.buffered().use { fileOutputStream ->
                        fileOutputStream.write(byteArray)
                    }
                } catch (ex: IOException) {
                    println(ex.message)
                } finally {
                    try {
                        destFileName?.close()
                    } catch (ex: IOException) {
                        println(ex.message)
                    }
                }
//                val destFileName = filePhoto.outputStream
//
//                destFileName.buffered().use { fileOutputStream ->
//                    fileOutputStream.write(byteArray)
//                }
//                Timber.d("time = ${System.currentTimeMillis() - start}")
                true
            }
        }
    }

    // Получаем список фото с сервера
    suspend fun getImages(date: String): List<Image> {
        val images = mutableListOf<Image>()
        withContext(Dispatchers.Default) {
            // Создаем архитектуру папок на сервере и получаем путь к папке
            val path = Utils.createFolderOnServer(date) + File.separator

            // Создаем объект аутентификатор
            val authenticationContext = AuthenticationContext(
                LoginInformation.USER,
                LoginInformation.PASS.toCharArray(),
                ""
            )
            SmbConnection(
                LoginInformation.SERVERNAME,
                LoginInformation.SHARENAME,
                authenticationContext
            ).use { smbConnection ->
                // Ресолвим путь назначения в SmbFile
                val baseDir = SmbDirectory(smbConnection, path)
//                val baseDir =
//                    SmbFile(smbConnection, path)

                Timber.d("path = $path")
                val files = baseDir.files
                for (i in files.indices) {
                    val fileName = files[i].name
                    val extension = fileName.substring(fileName.lastIndexOf(".") + 1)
                    if (extension != "jpeg") {
                        continue
                    }
                    val smbFile = files[i]
                    val storageDir: File? =
                        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val targetFile = File("$storageDir/$fileName")
                    if (!targetFile.exists()) {
                        targetFile.createNewFile()
                    }

                    var smbFileInputStream: InputStream? = null
                    try {
                        smbFileInputStream = smbFile.inputStream
                        smbFileInputStream.use {
                            Files.copy(
                                it,
                                targetFile.toPath(),
                                StandardCopyOption.REPLACE_EXISTING
                            )
                        }
                    } catch (ex: IOException) {
                        println(ex.message)
                    } finally {
                        try {
                            smbFileInputStream?.close()
                        } catch (ex: IOException) {
                            println(ex.message)
                        }
                    }

//                    val smbFileInputStream = smbFile.inputStream
//                    smbFileInputStream.use {
//                        Files.copy(
//                            it,
//                            targetFile.toPath(),
//                            StandardCopyOption.REPLACE_EXISTING
//                        )
//                    }
//                    smbFileInputStream.close()

//                smbFile.inputStream.use { inputStream ->
//                    Files.copy(
//                        it,
//                        targetFile.toPath(),
//                        StandardCopyOption.REPLACE_EXISTING
//                    )
//                }
//
//                smbFile.inputStream.use { inputStream ->
//                    Files.copy(
//                        inputStream,
//                        targetFile.toPath(),
//                        StandardCopyOption.REPLACE_EXISTING
//                    )
//                }

//                smbFile.inputStream.close()
//                smbFile.outputStream.close()

//                images += Image(i.toLong(), targetFile, fileName, files[i].length().toInt())
                    images += Image(i.toLong(), targetFile, fileName, files[i].fileSize.toInt())
                }
            }
        }
        return images
    }

    // Загружаем с сервера строку json по выбранной дате
    suspend fun downloadFileToServer(year: String, month: String, day: String): String {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) ""

        val authenticationContext =
            AuthenticationContext(
                LoginInformation.USER,
                LoginInformation.PASS.toCharArray(),
                ""
            )
        // Ресолвим путь назначения в SmbFile
        SmbConnection(
            LoginInformation.SERVERNAME,
            LoginInformation.SHARENAME,
//            "Public/ОТК/Проверка настроек/$year/$month/$day/${LoginInformation.NAME_FILE}",
            authenticationContext
        ).use { smbConnection ->
            val path = LoginInformation.PATH
//            val path = "ОТК/Проверка настроек/"
            val smbFile =
                SmbFile(smbConnection, "$path/$year/$month/$day/${LoginInformation.NAME_FILE}")

            var inputStream: InputStream? = null
            var stringJsonSettings = ""
            try {
                inputStream = smbFile.inputStream

                inputStream.bufferedReader().use {
                    stringJsonSettings = it.readText()
                }
            } catch (_: IOException) {
            } finally {
                try {
                    inputStream?.close()
                } catch (ex: IOException) {
                    println(ex.message)
                }
            }
            return stringJsonSettings
        }
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
                            "П1-07" -> {
                                assemblyEB?.wp07?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-08" -> {
                                assemblyEB?.wp08?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-09" -> {
                                assemblyEB?.wp09?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-10" -> {
                                assemblyEB?.wp10?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                        }
                    }

                    context.resources.getString(R.string.assembly_bip) -> {
                        val assemblyBIP = assemblyWrapper?.assemblyBIP
                        when (workPlace) {
                            "П1-11" -> {
                                assemblyBIP?.wp11?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-12" -> {
                                assemblyBIP?.wp12?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-13" -> {
                                assemblyBIP?.wp13?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-14" -> {
                                assemblyBIP?.wp14?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-15" -> {
                                assemblyBIP?.wp15?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-16" -> {
                                assemblyBIP?.wp16?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }
                        }
                    }

                    context.resources.getString(R.string.assembly_temp) -> {
                        val solderingTemperature = assemblyWrapper?.solderingTemperature
                        when (workPlace) {
                            "П1-01" -> {
                                solderingTemperature?.wp01?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-02" -> {
                                solderingTemperature?.wp02?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-03" -> {
                                solderingTemperature?.wp03?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-04" -> {
                                solderingTemperature?.wp04?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-05" -> {
                                solderingTemperature?.wp05?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-19" -> {
                                solderingTemperature?.wp19?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-20" -> {
                                solderingTemperature?.wp20?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-21" -> {
                                solderingTemperature?.wp21?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-22" -> {
                                solderingTemperature?.wp22?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-23" -> {
                                solderingTemperature?.wp23?.apply {
                                    list.put(currentDate, ValueAndCalibration(value, isCalibration))
                                }
                            }

                            "П1-24" -> {
                                solderingTemperature?.wp24?.apply {
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

    suspend fun downloadStatisticsOutage(
        year: Int,
        month: Int,
        day: Int,
        operation: String
    ): HashMap<Date, Boolean> {
        return withContext(Dispatchers.IO) {
            val list = hashMapOf<Date, Boolean>()
            val adapterAssembly =
                download(year.toString(), month.toString(), day.toString())?.outageWrapper

            val currentDate = GregorianCalendar(year, month - 1, day).time
            val listAssembly = hashMapOf<Date, CheckingSettingsCustomAdapter.OutageWrapper?>()

            listAssembly.put(currentDate, adapterAssembly)
            listAssembly.values.forEach { assemblyWrapper ->
                when (operation) {
                    context.resources.getString(R.string.outageText) -> {
                        val outage = assemblyWrapper?.checkingOutage
                        outage?.outage?.apply {
                            list.put(currentDate, this)
                        }
                    }
                }
            }

//            Timber.d("$day list = ${list}")
            return@withContext list
        }
    }

    suspend fun downloadStatisticsExitDevice(
        year: Int,
        month: Int,
        day: Int,
        operation: String
    ): HashMap<Date, Utils.MyHash> {
        return withContext(Dispatchers.IO) {
            val list = hashMapOf<Date, Utils.MyHash>()
            val adapterAssembly =
                download(year.toString(), month.toString(), day.toString())?.exitDeviceWrapper

            val currentDate = GregorianCalendar(year, month - 1, day).time
            val listAssembly = hashMapOf<Date, CheckingSettingsCustomAdapter.ExitDeviceWrapper?>()

            listAssembly.put(currentDate, adapterAssembly)
            listAssembly.values.forEach { assemblyWrapper ->
                when (operation) {
                    context.resources.getString(R.string.exitDevice) -> {
                        val outage = assemblyWrapper?.exitDevice
                        list.put(currentDate, Utils.MyHash(outage?.exitDevice, outage?.noteExitDevice))
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
            val authenticationContext =
                AuthenticationContext(
                    LoginInformation.USER,
                    LoginInformation.PASS.toCharArray(),
                    ""
                )
            val str = "\""
            // Ресолвим путь назначения в SmbFile
            SmbConnection(
                LoginInformation.SERVERNAME,
                LoginInformation.SHARENAME,
//                "Public/ОТК/Проверка настроек/$year/$month/$day/${LoginInformation.NAME_FILE}",
                authenticationContext
            ).use { smbConnection ->
                // Ищем файл
                val file = SmbFile(
                    smbConnection,
                    path + File.separator + name
                )
                Timber.d("delete file = ${path + File.separator + name}")
                file.deleteFile()
            }
            true
        }
    }

    // Проверяет существует ли файл настроек
    suspend fun isExistFile(year: String, month: String, day: String): Boolean {
        return withContext(Dispatchers.Default) {
            // Создаем объект аутентификатор
            val authenticationContext =
                AuthenticationContext(
                    LoginInformation.USER,
                    LoginInformation.PASS.toCharArray(),
                    ""
                )
            val str = "\""
            // Ресолвим путь назначения в SmbFile
            SmbConnection(
                LoginInformation.SERVERNAME,
                LoginInformation.SHARENAME,
//                "Public/ОТК/Проверка настроек/$year/$month/$day/${LoginInformation.NAME_FILE}",
                authenticationContext
            ).use { smbConnection ->
                val smbDirectoryYear =
                    SmbDirectory(smbConnection, LoginInformation.PATH + "/$year")
                if (!smbDirectoryYear.isDirectory)
                    smbDirectoryYear.createDirectory()

                val smbDirectoryMonth =
                    SmbDirectory(smbConnection, LoginInformation.PATH + "/$year/$month")
                if (!smbDirectoryMonth.isDirectory)
                    smbDirectoryMonth.createDirectory()

                val smbDirectoryDay =
                    SmbDirectory(smbConnection, LoginInformation.PATH + "/$year/$month/$day")
                if (!smbDirectoryDay.isDirectory)
                    smbDirectoryDay.createDirectory()

//                smbDirectory.ensureExists();

                val smbFile = SmbFile(
                    smbConnection,
                    LoginInformation.PATH + "/$year/$month/$day/${LoginInformation.NAME_FILE}"
                )
                if (!smbFile.isExisting)
                    smbFile.createFile()
                return@withContext true
            }
        }
    }
}