package ru.popov.checkingsettings.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
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
        val bipTest: BipTestWrapper?,
        val testConnectorWrapper: TestConnectorWrapper?,
        val outageWrapper: OutageWrapper?,
        val exitDeviceWrapper: ExitDeviceWrapper?
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
        val wp33: Boolean?,
        val wp34: Boolean?,
        val wp36: Boolean?,
        val wp37: Boolean?,
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
        val wp07: ValueAndCalibration?,
        val wp08: ValueAndCalibration?,
        val wp09: ValueAndCalibration?,
        val wp10: ValueAndCalibration?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class AssemblyBIP(
        val wp11: ValueAndCalibration?,
        val wp12: ValueAndCalibration?,
        val wp13: ValueAndCalibration?,
        val wp14: ValueAndCalibration?,
        val wp15: ValueAndCalibration?,
        val wp16: ValueAndCalibration?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class SolderingTemperature(
        val wp01: ValueAndCalibration?,
        val wp02: ValueAndCalibration?,
        val wp03: ValueAndCalibration?,
        val wp04: ValueAndCalibration?,
        val wp05: ValueAndCalibration?,
        val wp19: ValueAndCalibration?,
        val wp20: ValueAndCalibration?,
        val wp21: ValueAndCalibration?,
        val wp22: ValueAndCalibration?,
        val wp23: ValueAndCalibration?,
        val wp24: ValueAndCalibration?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class AssemblyWrapper(
        val assemblyEB: AssemblyEB,
        val assemblyBIP: AssemblyBIP,
        val solderingTemperature: SolderingTemperature
    )

    /* Speaker Test */
    @JsonClass(generateAdapter = true)
    data class CheckSoundSignalWhenSpeakerConnected(
        val wp25: Boolean?,
        val wp26: Boolean?,
        val wpGold25: Boolean?,
        val wpGold26: Boolean?,
        val note: String?,
        val noteGold: String?
    )

    @JsonClass(generateAdapter = true)
    data class SpeakerTestWrapper(
        val checkSoundSignalWhenSpeakerConnected: CheckSoundSignalWhenSpeakerConnected,
    )

    /* Bip Test */
    @JsonClass(generateAdapter = true)
    data class GoldBipTest(
        val wp17: Boolean?,
        val wp18: Boolean?,
        val note: String?,
    )

    @JsonClass(generateAdapter = true)
    data class BipTestWrapper(
        val goldTest: GoldBipTest,
    )

    /* TestConnector */
    @JsonClass(generateAdapter = true)
    data class CheckingGold(
        val wp35: Boolean?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class TestConnectorWrapper(
        val checkingGold: CheckingGold
    )

    /* Outage */
    @JsonClass(generateAdapter = true)
    data class CheckingOutage(
        val outage: Boolean?,
        val note: String?
    )

    @JsonClass(generateAdapter = true)
    data class OutageWrapper(
        val checkingOutage: CheckingOutage
    )

    /* ExitDevice */
    @JsonClass(generateAdapter = true)
    data class ExitDevice(
        val exitDevice: Boolean?,
        val noteExitDevice: String?
    )

    @JsonClass(generateAdapter = true)
    data class ExitDeviceWrapper(
        val exitDevice: ExitDevice
    )
}