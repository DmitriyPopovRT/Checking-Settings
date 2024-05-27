package ru.popov.checkingsettings.utils

import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter

object JsonSettings {
    var packageJson: CheckingSettingsCustomAdapter.PackageWrapper? = null
    var programJson: CheckingSettingsCustomAdapter.ProgramTestWrapper? = null
    var assemblyAndLabelJson: CheckingSettingsCustomAdapter.AssemblyAndLabelWrapper? = null
    var assemblyJson: CheckingSettingsCustomAdapter.AssemblyWrapper? = null
    var speakerTestJson: CheckingSettingsCustomAdapter.SpeakerTestWrapper? = null
    var bipTestJson: CheckingSettingsCustomAdapter.BipTestWrapper? = null
    var testConnectorJson: CheckingSettingsCustomAdapter.TestConnectorWrapper? = null
    var outageJson: CheckingSettingsCustomAdapter.OutageWrapper? = null
    var exitDeviceJson: CheckingSettingsCustomAdapter.ExitDeviceWrapper? = null
}