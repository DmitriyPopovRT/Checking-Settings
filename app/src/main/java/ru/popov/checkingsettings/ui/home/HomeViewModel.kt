package ru.popov.checkingsettings.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.popov.checkingsettings.data.Image
import ru.popov.checkingsettings.data.Repository
import ru.popov.checkingsettings.utils.SingleLiveEvent
import ru.popov.checkingsettings.utils.Utils.haveQ
import timber.log.Timber

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = Repository(application)

    private val stringJsonSettingsLiveData = SingleLiveEvent<String>()
    private val downloadStringJsonSettingsLiveData = SingleLiveEvent<String>()
    private val isSendingLiveData = SingleLiveEvent<Boolean>()
    private val isSendingImagesLiveData = SingleLiveEvent<Boolean>()
    private val isErrorSettingsLiveData = SingleLiveEvent<String>()
    private val imagesMutableLiveData = SingleLiveEvent<List<Image>>()
    private val isSendingPhotoLiveData = SingleLiveEvent<Boolean>()
    private val isDeletePhotoLiveData = SingleLiveEvent<Boolean>()
    private val isFileSettingsExistLiveData = SingleLiveEvent<Boolean>()
    private val permissionsGrantedMutableLiveData = MutableLiveData(true)

    val stringJsonSettings: LiveData<String>
        get() = stringJsonSettingsLiveData
    val downloadStringJsonSettings: LiveData<String>
        get() = downloadStringJsonSettingsLiveData

    val isError: LiveData<String>
        get() = isErrorSettingsLiveData
    val isSending: LiveData<Boolean>
        get() = isSendingLiveData
    val isSendingImages: LiveData<Boolean>
        get() = isSendingImagesLiveData
    val isSendingPhoto: LiveData<Boolean>
        get() = isSendingPhotoLiveData
    val isDeletePhoto: LiveData<Boolean>
        get() = isDeletePhotoLiveData
    val isFileSettingsExist: LiveData<Boolean>
        get() = isFileSettingsExistLiveData
    val imagesLiveData: LiveData<List<Image>>
        get() = imagesMutableLiveData
    val permissionsGrantedLiveData: LiveData<Boolean>
        get() = permissionsGrantedMutableLiveData

    fun generateJson() {
        viewModelScope.launch {
            val settingsJsonString = repository.generateJsonString()
            stringJsonSettingsLiveData.postValue(settingsJsonString)
        }
    }

    fun convertPackageResultToJson(
        wp29: Boolean?,
        textNote: String
    ) {
        viewModelScope.launch {
            repository.convertPackageResultToJson(wp29, textNote)
        }
    }

    fun convertPackageResultToJson(
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
        viewModelScope.launch {
            repository.convertProgramResultToJson(
                wp22Mod,
                wp23Mod,
                wp24Mod,
                wp25Mod,
                wp26Mod,
                wp27Mod,
                textNote,
                wp22Version,
                wp23Version,
                wp24Version,
                wp25Version,
                wp26Version,
                wp27Version,
                textNoteVersion
            )
        }
    }

    fun convertAssemblyAndLabelResultToJson(
        wp28: Boolean?,
        textNote: String
    ) {
        viewModelScope.launch {
            repository.convertAssemblyAndLabelResultToJson(wp28, textNote)
        }
    }

    fun convertAssemblyResultToJson(
        wp11AssemblyEB: String?,
        wp12AssemblyEB: String?,
        wp13AssemblyEB: String?,
        wp14AssemblyEB: String?,
        wp15AssemblyEB: String?,
        wp16AssemblyEB: String?,
        wp17AssemblyEB: String?,
        wp18AssemblyEB: String?,
        textNoteAssemblyEB: String?,
        wp11AssemblyBip: String?,
        wp12AssemblyBip: String?,
        wp13AssemblyBip: String?,
        wp14AssemblyBip: String?,
        wp15AssemblyBip: String?,
        wp16AssemblyBip: String?,
        wp17AssemblyBip: String?,
        wp18AssemblyBip: String?,
        textNoteAssemblyBip: String?,
        wp11AssemblySpeaker: String?,
        wp12AssemblySpeaker: String?,
        wp13AssemblySpeaker: String?,
        wp14AssemblySpeaker: String?,
        wp15AssemblySpeaker: String?,
        wp16AssemblySpeaker: String?,
        wp17AssemblySpeaker: String?,
        wp18AssemblySpeaker: String?,
        textNoteAssemblySpeaker: String?,
        wp11AssemblySolderingTemperature: String?,
        wp12AssemblySolderingTemperature: String?,
        wp13AssemblySolderingTemperature: String?,
        wp14AssemblySolderingTemperature: String?,
        wp15AssemblySolderingTemperature: String?,
        wp16AssemblySolderingTemperature: String?,
        wp17AssemblySolderingTemperature: String?,
        wp18AssemblySolderingTemperature: String?,
        textNoteAssemblySolderingTemperature: String?,
        wp23AssemblyFixing: String?,
        wp24AssemblyFixing: String?,
        wp25AssemblyFixing: String?,
        wp26AssemblyFixing: String?,
        wp27AssemblyFixing: String?,
        wp28AssemblyFixing: String?,
        textNoteAssemblyFixing: String?
    ) {
        viewModelScope.launch {
            repository.convertAssemblyResultToJson(
                wp11AssemblyEB,
                wp12AssemblyEB,
                wp13AssemblyEB,
                wp14AssemblyEB,
                wp15AssemblyEB,
                wp16AssemblyEB,
                wp17AssemblyEB,
                wp18AssemblyEB,
                textNoteAssemblyEB,
                wp11AssemblyBip,
                wp12AssemblyBip,
                wp13AssemblyBip,
                wp14AssemblyBip,
                wp15AssemblyBip,
                wp16AssemblyBip,
                wp17AssemblyBip,
                wp18AssemblyBip,
                textNoteAssemblyBip,
                wp11AssemblySpeaker,
                wp12AssemblySpeaker,
                wp13AssemblySpeaker,
                wp14AssemblySpeaker,
                wp15AssemblySpeaker,
                wp16AssemblySpeaker,
                wp17AssemblySpeaker,
                wp18AssemblySpeaker,
                textNoteAssemblySpeaker,
                wp11AssemblySolderingTemperature,
                wp12AssemblySolderingTemperature,
                wp13AssemblySolderingTemperature,
                wp14AssemblySolderingTemperature,
                wp15AssemblySolderingTemperature,
                wp16AssemblySolderingTemperature,
                wp17AssemblySolderingTemperature,
                wp18AssemblySolderingTemperature,
                textNoteAssemblySolderingTemperature,
                wp23AssemblyFixing,
                wp24AssemblyFixing,
                wp25AssemblyFixing,
                wp26AssemblyFixing,
                wp27AssemblyFixing,
                wp28AssemblyFixing,
                textNoteAssemblyFixing
            )
        }
    }

    fun convertSpeakerTestResultToJson(
        wp111: Boolean?,
        textNote: String
    ) {
        viewModelScope.launch {
            repository.convertSpeakerTestResultToJson(wp111, textNote)
        }
    }

    fun updatePermissionState(isGranted: Boolean, date: String) {
        if (isGranted) {
            permissionsGranted(date)
        } else {
            permissionsDenied()
        }
    }

    fun permissionsGranted(date: String) {
        if (haveQ()) {
            loadImages(date)
        }

        permissionsGrantedMutableLiveData.postValue(true)
    }

    fun permissionsDenied() {
        permissionsGrantedMutableLiveData.postValue(false)
    }

    fun saveFileToServer(strJson: String, date: String) {
        viewModelScope.launch {
//            isLoadingLiveData.postValue(true)
            try {
                val result = repository.saveFileToServer(strJson, date)
                if (result) {
                    isSendingLiveData.postValue(true)
                }
            } catch (t: Throwable) {
                Timber.e(t)
                isErrorSettingsLiveData.postValue(t.message)
//                isErrorLiveData.postValue("")
            } finally {
//                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun savePhotoToServer(byteArray: ByteArray, date: String) {
        viewModelScope.launch {
//            isLoadingLiveData.postValue(true)
            try {
                val result = repository.savePhotoToServer(byteArray, date)
                if (result) {
                    isSendingPhotoLiveData.postValue(true)
                }
            } catch (t: Throwable) {
                Timber.e(t)
//                isErrorSettingsLiveData.postValue(t.message)
//                isErrorLiveData.postValue("")
            } finally {
//                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun downloadFileToServer(year: String, month: String, day: String) {
        viewModelScope.launch {
//            isLoadingLiveData.postValue(true)
            try {
                val result = repository.downloadFileToServer(year, month, day)
                downloadStringJsonSettingsLiveData.postValue(result)
            } catch (t: Throwable) {
                Timber.e(t)
                isErrorSettingsLiveData.postValue(t.message)
            } finally {
//                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun loadImages(date: String) {
        viewModelScope.launch {
            try {
                isSendingImagesLiveData.postValue(true)
                val images = repository.getImages(date)
                imagesMutableLiveData.postValue(images)
            } catch (t: Throwable) {
                Timber.e(t)
//                imagesMutableLiveData.postValue(emptyList())
//                toastSingleLiveEvent.postValue(R.string.image_list_error)
            } finally {
                isSendingImagesLiveData.postValue(false)
            }
        }
    }

    fun deleteAllFilesFolder() {
        viewModelScope.launch {
            repository.deleteAllFilesFolder()
        }
    }

    fun deleteImage(date: String, name: String) {
        viewModelScope.launch {
            try {
                Timber.d("delete $name date = $date")
                val result = repository.deleteImage(date, name)
                if (result) {
                    isDeletePhotoLiveData.postValue(true)
                }
            } catch (e: Exception) {
                Timber.e(e)
                isErrorSettingsLiveData.postValue(e.message)
            }

        }
    }

    fun isExistFile(year: String, month: String, day: String) {
        viewModelScope.launch {
            try {
                val result = repository.isExistFile(year, month, day)
                isFileSettingsExistLiveData.postValue(result)
            } catch (e: Exception) {
                Timber.e(e)
                isErrorSettingsLiveData.postValue(e.message)
            }
        }
    }
}