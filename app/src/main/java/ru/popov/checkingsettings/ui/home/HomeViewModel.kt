package ru.popov.checkingsettings.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.popov.checkingsettings.data.Repository

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = Repository(application)

    private val stringJsonSettingsLiveData = MutableLiveData<String>()
    private val downloadStringJsonSettingsLiveData = MutableLiveData<String>()
    private val isSendingLiveData = MutableLiveData<Boolean>()
    private val isDownloadSettingsLiveData = MutableLiveData<Boolean>()
    private val isErrorSettingsLiveData = MutableLiveData<String>()

    val stringJsonSettings: LiveData<String>
        get() = stringJsonSettingsLiveData
    val downloadStringJsonSettings: LiveData<String>
        get() = downloadStringJsonSettingsLiveData

    val isError: LiveData<String>
        get() = isErrorSettingsLiveData
    val isSending: LiveData<Boolean>
        get() = isSendingLiveData
    val isDownloadSettings: LiveData<Boolean>
        get() = isDownloadSettingsLiveData

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
        viewModelScope.launch {
            repository.convertAssemblyResultToJson(
                wp12AssemblyEB,
                wp13AssemblyEB,
                wp14AssemblyEB,
                wp15AssemblyEB,
                wp16AssemblyEB,
                wp17AssemblyEB,
                wp18AssemblyEB,
                textNoteAssemblyEB,
                wp12AssemblyBip,
                wp13AssemblyBip,
                wp14AssemblyBip,
                wp15AssemblyBip,
                wp16AssemblyBip,
                wp17AssemblyBip,
                wp18AssemblyBip,
                textNoteAssemblyBip,
                wp12AssemblySpeaker,
                wp13AssemblySpeaker,
                wp14AssemblySpeaker,
                wp15AssemblySpeaker,
                wp16AssemblySpeaker,
                wp17AssemblySpeaker,
                wp18AssemblySpeaker,
                textNoteAssemblySpeaker,
                wp12AssemblySolderingTemperature,
                wp13AssemblySolderingTemperature,
                wp14AssemblySolderingTemperature,
                wp15AssemblySolderingTemperature,
                wp16AssemblySolderingTemperature,
                wp17AssemblySolderingTemperature,
                wp18AssemblySolderingTemperature,
                textNoteAssemblySolderingTemperature
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

    fun saveFileToServer(strJson: String, date: String) {
        viewModelScope.launch {
//            isLoadingLiveData.postValue(true)
            try {
                val result = repository.saveFileToServer(strJson, date)
                if (result) {
                    isSendingLiveData.postValue(true)
                }
            } catch (t: Throwable) {
                isErrorSettingsLiveData.postValue(t.message)
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
                isDownloadSettingsLiveData.postValue(true)
            } catch (t: Throwable) {
                isErrorSettingsLiveData.postValue(t.message)
            } finally {
//                isLoadingLiveData.postValue(false)
            }
        }
    }
}