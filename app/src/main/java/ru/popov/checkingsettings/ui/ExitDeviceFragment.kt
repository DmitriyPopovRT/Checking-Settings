package ru.popov.checkingsettings.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentExitDeviceBinding
import ru.popov.checkingsettings.databinding.FragmentPackageBinding
import ru.popov.checkingsettings.databinding.FragmentTestConnectorBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils
import ru.popov.checkingsettings.utils.Utils.checkedImageButton

class ExitDeviceFragment : Fragment(R.layout.fragment_exit_device) {

    private val binding: FragmentExitDeviceBinding by viewBinding(FragmentExitDeviceBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var exitDevice: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOkExitDevice.setOnClickListener {
            exitDevice = checkedImageButton(
                it,
                binding.buttonOkExitDevice,
                binding.buttonCloseExitDevice,
                requireContext()
            )
        }

        binding.buttonCloseExitDevice.setOnClickListener {
            exitDevice = checkedImageButton(
                it,
                binding.buttonOkExitDevice,
                binding.buttonCloseExitDevice,
                requireContext()
            )
        }
    }

    fun setSettingsExitDevice(exitDeviceWrapper: CheckingSettingsCustomAdapter.ExitDeviceWrapper) {
        exitDeviceWrapper.exitDevice.exitDevice?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOkExitDevice,
                buttonClose = binding.buttonCloseExitDevice,
                context = requireContext()
            )
        }
        binding.editTextExitDevice.setText(exitDeviceWrapper.exitDevice.noteExitDevice)
    }

    fun clearView() {
        with(binding) {
            buttonOkExitDevice.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkExitDevice.isActivated = false
            buttonCloseExitDevice.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseExitDevice.isActivated = false
            editTextExitDevice.text.clear()
            exitDevice = null
        }
    }

    fun onClickSendServer() {
        exitDevice = when {
            binding.buttonOkExitDevice.isActivated -> true
            binding.buttonCloseExitDevice.isActivated -> false
            else -> null
        }
        viewModel.convertExitDeviceResultToJson(exitDevice, binding.editTextExitDevice.text.toString())
    }
}