package ru.popov.checkingsettings.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentProgrammTestBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils

class ProgramTestFragment : Fragment(R.layout.fragment_programm_test) {

    private val binding: FragmentProgrammTestBinding by viewBinding(FragmentProgrammTestBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var wp22Mod: Boolean? = null
    private var wp23Mod: Boolean? = null
    private var wp24Mod: Boolean? = null
    private var wp25Mod: Boolean? = null
    private var wp26Mod: Boolean? = null
    private var wp27Mod: Boolean? = null

    private var wp22Version: Boolean? = null
    private var wp23Version: Boolean? = null
    private var wp24Version: Boolean? = null
    private var wp25Version: Boolean? = null
    private var wp26Version: Boolean? = null
    private var wp27Version: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    fun onClickSendServer() {
        wp22Mod = when {
            binding.buttonOk22.isActivated -> true
            binding.buttonClose22.isActivated -> false
            else -> null
        }
        wp23Mod = when {
            binding.buttonOk23.isActivated -> true
            binding.buttonClose23.isActivated -> false
            else -> null
        }
        wp24Mod = when {
            binding.buttonOk24.isActivated -> true
            binding.buttonClose24.isActivated -> false
            else -> null
        }
        wp25Mod = when {
            binding.buttonOk25.isActivated -> true
            binding.buttonClose25.isActivated -> false
            else -> null
        }
        wp26Mod = when {
            binding.buttonOk26.isActivated -> true
            binding.buttonClose26.isActivated -> false
            else -> null
        }
        wp27Mod = when {
            binding.buttonOk27.isActivated -> true
            binding.buttonClose27.isActivated -> false
            else -> null
        }

        wp22Version = when {
            binding.buttonOkVersion22.isActivated -> true
            binding.buttonCloseVersion22.isActivated -> false
            else -> null
        }
        wp23Version = when {
            binding.buttonOkVersion23.isActivated -> true
            binding.buttonCloseVersion23.isActivated -> false
            else -> null
        }
        wp24Version = when {
            binding.buttonOkVersion24.isActivated -> true
            binding.buttonCloseVersion24.isActivated -> false
            else -> null
        }
        wp25Version = when {
            binding.buttonOkVersion25.isActivated -> true
            binding.buttonCloseVersion25.isActivated -> false
            else -> null
        }
        wp26Version = when {
            binding.buttonOkVersion26.isActivated -> true
            binding.buttonCloseVersion26.isActivated -> false
            else -> null
        }
        wp27Version = when {
            binding.buttonOkVersion27.isActivated -> true
            binding.buttonCloseVersion27.isActivated -> false
            else -> null
        }

        viewModel.convertPackageResultToJson(
            wp22Mod,
            wp23Mod,
            wp24Mod,
            wp25Mod,
            wp26Mod,
            wp27Mod,
            binding.editTextMod.text.toString(),
            wp22Version,
            wp23Version,
            wp24Version,
            wp25Version,
            wp26Version,
            wp27Version,
            binding.editTextVersion.text.toString()
        )
    }

    fun clearView() {
        with(binding) {
            buttonOk22.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk22.isActivated = false
            buttonClose22.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose22.isActivated = false

            buttonOk23.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk23.isActivated = false
            buttonClose23.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose23.isActivated = false

            buttonOk24.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk24.isActivated = false
            buttonClose24.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose24.isActivated = false

            buttonOk25.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk25.isActivated = false
            buttonClose25.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose25.isActivated = false

            buttonOk26.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk26.isActivated = false
            buttonClose26.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose26.isActivated = false

            buttonOk27.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk27.isActivated = false
            buttonClose27.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose27.isActivated = false


            buttonOkVersion22.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkVersion22.isActivated = false
            buttonCloseVersion22.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseVersion22.isActivated = false

            buttonOkVersion23.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkVersion23.isActivated = false
            buttonCloseVersion23.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseVersion23.isActivated = false

            buttonOkVersion24.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkVersion24.isActivated = false
            buttonCloseVersion24.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseVersion24.isActivated = false

            buttonOkVersion25.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkVersion25.isActivated = false
            buttonCloseVersion25.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseVersion25.isActivated = false

            buttonOkVersion26.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkVersion26.isActivated = false
            buttonCloseVersion26.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseVersion26.isActivated = false

            buttonOkVersion27.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkVersion27.isActivated = false
            buttonCloseVersion27.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseVersion27.isActivated = false

            editTextMod.text.clear()
            editTextVersion.text.clear()

            wp22Mod = null
            wp23Mod = null
            wp24Mod = null
            wp25Mod = null
            wp26Mod = null
            wp27Mod = null
            wp22Version = null
            wp23Version = null
            wp24Version = null
            wp25Version = null
            wp26Version = null
            wp27Version = null
        }
    }

    fun setSettingsProgram(programWrapper: CheckingSettingsCustomAdapter.ProgramTestWrapper) {
        programWrapper.modMatchesProduction.let { modMatchesProduction ->
            modMatchesProduction.wp22?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOk22,
                    buttonClose = binding.buttonClose22,
                    context = requireContext()
                )
            }
            modMatchesProduction.wp23?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOk23,
                    buttonClose = binding.buttonClose23,
                    context = requireContext()
                )
            }
            modMatchesProduction.wp24?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOk24,
                    buttonClose = binding.buttonClose24,
                    context = requireContext()
                )
            }
            modMatchesProduction.wp25?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOk25,
                    buttonClose = binding.buttonClose25,
                    context = requireContext()
                )
            }
            modMatchesProduction.wp26?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOk26,
                    buttonClose = binding.buttonClose26,
                    context = requireContext()
                )
            }
            modMatchesProduction.wp27?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOk27,
                    buttonClose = binding.buttonClose27,
                    context = requireContext()
                )
            }
            binding.editTextMod.setText(modMatchesProduction.note)
        }

        programWrapper.firmwareVersionIsCurrent.let { firmwareVersionIsCurrent ->
            firmwareVersionIsCurrent.wp22?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOkVersion22,
                    buttonClose = binding.buttonCloseVersion22,
                    context = requireContext()
                )
            }
            firmwareVersionIsCurrent.wp23?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOkVersion23,
                    buttonClose = binding.buttonCloseVersion23,
                    context = requireContext()
                )
            }
            firmwareVersionIsCurrent.wp24?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOkVersion24,
                    buttonClose = binding.buttonCloseVersion24,
                    context = requireContext()
                )
            }
            firmwareVersionIsCurrent.wp25?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOkVersion25,
                    buttonClose = binding.buttonCloseVersion25,
                    context = requireContext()
                )
            }
            firmwareVersionIsCurrent.wp26?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOkVersion26,
                    buttonClose = binding.buttonCloseVersion26,
                    context = requireContext()
                )
            }
            firmwareVersionIsCurrent.wp27?.let {
                Utils.setCheckedImageButtonDownloadServer(
                    isChecked = it,
                    buttonOk = binding.buttonOkVersion27,
                    buttonClose = binding.buttonCloseVersion27,
                    context = requireContext()
                )
            }
            binding.editTextVersion.setText(firmwareVersionIsCurrent.note)
        }
    }

    private fun initButtons() {
        with(binding) {
            buttonOk22.setOnClickListener {
                wp22Mod = Utils.checkedImageButton(
                    it,
                    buttonOk22,
                    buttonClose22,
                    requireContext()
                )
            }
            buttonClose22.setOnClickListener {
                wp22Mod = Utils.checkedImageButton(
                    it,
                    buttonOk22,
                    buttonClose22,
                    requireContext()
                )
            }

            buttonOk23.setOnClickListener {
                wp23Mod = Utils.checkedImageButton(
                    it,
                    buttonOk23,
                    buttonClose23,
                    requireContext()
                )
            }
            buttonClose23.setOnClickListener {
                wp23Mod = Utils.checkedImageButton(
                    it,
                    buttonOk23,
                    buttonClose23,
                    requireContext()
                )
            }

            buttonOk24.setOnClickListener {
                wp24Mod = Utils.checkedImageButton(
                    it,
                    buttonOk24,
                    buttonClose24,
                    requireContext()
                )
            }
            buttonClose24.setOnClickListener {
                wp24Mod = Utils.checkedImageButton(
                    it,
                    buttonOk24,
                    buttonClose24,
                    requireContext()
                )
            }

            buttonOk25.setOnClickListener {
                wp25Mod = Utils.checkedImageButton(
                    it,
                    buttonOk25,
                    buttonClose25,
                    requireContext()
                )
            }
            buttonClose25.setOnClickListener {
                wp25Mod = Utils.checkedImageButton(
                    it,
                    buttonOk25,
                    buttonClose25,
                    requireContext()
                )
            }

            buttonOk26.setOnClickListener {
                wp26Mod = Utils.checkedImageButton(
                    it,
                    buttonOk26,
                    buttonClose26,
                    requireContext()
                )
            }
            buttonClose26.setOnClickListener {
                wp26Mod = Utils.checkedImageButton(
                    it,
                    buttonOk26,
                    buttonClose26,
                    requireContext()
                )
            }

            buttonOk27.setOnClickListener {
                wp27Mod = Utils.checkedImageButton(
                    it,
                    buttonOk27,
                    buttonClose27,
                    requireContext()
                )
            }
            buttonClose27.setOnClickListener {
                wp27Mod = Utils.checkedImageButton(
                    it,
                    buttonOk27,
                    buttonClose27,
                    requireContext()
                )
            }


            buttonOkVersion22.setOnClickListener {
                wp22Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion22,
                        buttonCloseVersion22,
                        requireContext()
                    )
            }
            buttonCloseVersion22.setOnClickListener {
                wp22Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion22,
                        buttonCloseVersion22,
                        requireContext()
                    )
            }

            buttonOkVersion23.setOnClickListener {
                wp23Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion23,
                        buttonCloseVersion23,
                        requireContext()
                    )
            }
            buttonCloseVersion23.setOnClickListener {
                wp23Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion23,
                        buttonCloseVersion23,
                        requireContext()
                    )
            }

            buttonOkVersion24.setOnClickListener {
                wp24Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion24,
                        buttonCloseVersion24,
                        requireContext()
                    )
            }
            buttonCloseVersion24.setOnClickListener {
                wp24Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion24,
                        buttonCloseVersion24,
                        requireContext()
                    )
            }

            buttonOkVersion25.setOnClickListener {
                wp25Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion25,
                        buttonCloseVersion25,
                        requireContext()
                    )
            }
            buttonCloseVersion25.setOnClickListener {
                wp25Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion25,
                        buttonCloseVersion25,
                        requireContext()
                    )
            }

            buttonOkVersion26.setOnClickListener {
                wp26Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion26,
                        buttonCloseVersion26,
                        requireContext()
                    )
            }
            buttonCloseVersion26.setOnClickListener {
                wp26Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion26,
                        buttonCloseVersion26,
                        requireContext()
                    )
            }

            buttonOkVersion27.setOnClickListener {
                wp27Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion27,
                        buttonCloseVersion27,
                        requireContext()
                    )
            }
            buttonCloseVersion27.setOnClickListener {
                wp27Version =
                    Utils.checkedImageButton(
                        it,
                        buttonOkVersion27,
                        buttonCloseVersion27,
                        requireContext()
                    )
            }
        }
    }
}