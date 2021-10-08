package ru.popov.checkingsettings.utils

import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter

object JsonSettings {
    var packageJson: CheckingSettingsCustomAdapter.PackageWrapper? = null
    var programJson: CheckingSettingsCustomAdapter.ProgramTestWrapper? = null
    var assemblyAndLabelJson: CheckingSettingsCustomAdapter.AssemblyAndLabelWrapper? = null
    var assemblyJson: CheckingSettingsCustomAdapter.AssemblyWrapper? = null
    var speakerTestJson: CheckingSettingsCustomAdapter.SpeakerTestWrapper? = null
}