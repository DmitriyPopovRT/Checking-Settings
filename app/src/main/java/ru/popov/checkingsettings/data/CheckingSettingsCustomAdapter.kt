package ru.popov.checkingsettings.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

class CheckingSettingsCustomAdapter {

    @FromJson
    fun fromJson(customCheckingSettings: CustomCheckingSettings): CustomCheckingSettings {
        return customCheckingSettings
    }

    @ToJson
    fun toJson(customCheckingSettings: CustomCheckingSettings): CustomCheckingSettings {
        return customCheckingSettings
    }

    @JsonClass(generateAdapter = true)
    data class CustomCheckingSettings(
        val packaging: PackageWrapper?,
        val programTest: ProgramTestWrapper?,
        val assemblyAndLabel: AssemblyAndLabelWrapper?,
        val assembly: AssemblyWrapper?,
        val speakerTest: SpeakerTestWrapper?,
    )

    /* Packaged */
    @JsonClass(generateAdapter = true)
    data class CheckingPackagedProduct(
        val wp29: Boolean?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class PackageWrapper(
        val checkingPackagedProduct: CheckingPackagedProduct
    )

    /* Program Test */
    @JsonClass(generateAdapter = true)
    data class ModMatchesProduction(
        val wp22: Boolean?,
        val wp23: Boolean?,
        val wp24: Boolean?,
        val wp25: Boolean?,
        val wp26: Boolean?,
        val wp27: Boolean?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class FirmwareVersionIsCurrent(
        val wp22: Boolean?,
        val wp23: Boolean?,
        val wp24: Boolean?,
        val wp25: Boolean?,
        val wp26: Boolean?,
        val wp27: Boolean?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class ProgramTestWrapper(
        val modMatchesProduction: ModMatchesProduction,
        val firmwareVersionIsCurrent: FirmwareVersionIsCurrent
    )

    /* AssemblyAndLabel */
    @JsonClass(generateAdapter = true)
    data class ScanningLabelNumberNotFound(
        val wp28: Boolean?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class AssemblyAndLabelWrapper(
        val scanningLabelNumberNotFound: ScanningLabelNumberNotFound
    )

    /* Assembly */
    class ColorAdapter {
        @ToJson
        fun toJson(rgb: ValueAndCalibration): ValueAndCalibration {
            return rgb
        }

        @FromJson
        fun fromJson(rgb: String): ValueAndCalibration {
            return ValueAndCalibration(rgb, false)
        }
    }

    @JsonClass(generateAdapter = true)
    data class ValueAndCalibration(
        val value: String?,
        val isCalibration: Boolean? = false
    )

    @JsonClass(generateAdapter = true)
    data class AssemblyEB(
        val wp11: ValueAndCalibration?,
        val wp12: ValueAndCalibration?,
        val wp13: ValueAndCalibration?,
        val wp14: ValueAndCalibration?,
        val wp15: ValueAndCalibration?,
        val wp16: ValueAndCalibration?,
        val wp17: ValueAndCalibration?,
        val wp18: ValueAndCalibration?,
        val note: String?
    )
//    @JsonClass(generateAdapter = true)
//    data class AssemblyEB(
//        val wp11: String?,
//        val wp12: String?,
//        val wp13: String?,
//        val wp14: String?,
//        val wp15: String?,
//        val wp16: String?,
//        val wp17: String?,
//        val wp18: String?,
//        val note: String?
//    )

    @JsonClass(generateAdapter = true)
    data class AssemblyBIP(
        val wp11: ValueAndCalibration?,
        val wp12: ValueAndCalibration?,
        val wp13: ValueAndCalibration?,
        val wp14: ValueAndCalibration?,
        val wp15: ValueAndCalibration?,
        val wp16: ValueAndCalibration?,
        val wp17: ValueAndCalibration?,
        val wp18: ValueAndCalibration?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class AssemblySpeaker(
        val wp11: ValueAndCalibration?,
        val wp12: ValueAndCalibration?,
        val wp13: ValueAndCalibration?,
        val wp14: ValueAndCalibration?,
        val wp15: ValueAndCalibration?,
        val wp16: ValueAndCalibration?,
        val wp17: ValueAndCalibration?,
        val wp18: ValueAndCalibration?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class SolderingTemperature(
        val wp11: ValueAndCalibration?,
        val wp12: ValueAndCalibration?,
        val wp13: ValueAndCalibration?,
        val wp14: ValueAndCalibration?,
        val wp15: ValueAndCalibration?,
        val wp16: ValueAndCalibration?,
        val wp17: ValueAndCalibration?,
        val wp18: ValueAndCalibration?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class AssemblyFixing(
        val wp23: ValueAndCalibration?,
        val wp24: ValueAndCalibration?,
        val wp25: ValueAndCalibration?,
        val wp26: ValueAndCalibration?,
        val wp27: ValueAndCalibration?,
        val wp28: ValueAndCalibration?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class AssemblyWrapper(
        val assemblyEB: AssemblyEB,
        val assemblyBIP: AssemblyBIP,
        val assemblySpeaker: AssemblySpeaker,
        val solderingTemperature: SolderingTemperature,
        val assemblyFixing: AssemblyFixing?
    )

    /* Speaker Test */
    @JsonClass(generateAdapter = true)
    data class CheckSoundSignalWhenSpeakerConnected(
        val wp111: Boolean?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class SpeakerTestWrapper(
        val checkSoundSignalWhenSpeakerConnected: CheckSoundSignalWhenSpeakerConnected,
    )
}