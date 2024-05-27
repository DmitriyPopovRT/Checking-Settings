package ru.popov.checkingsettings.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentPackageBinding
import ru.popov.checkingsettings.databinding.FragmentTestConnectorBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils
import ru.popov.checkingsettings.utils.Utils.checkedImageButton

class TestConnectorFragment : Fragment(R.layout.fragment_test_connector) {

    private val binding: FragmentTestConnectorBinding by viewBinding(FragmentTestConnectorBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var wp35: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOkConnector.setOnClickListener {
            wp35 = checkedImageButton(
                it,
                binding.buttonOkConnector,
                binding.buttonCloseConnector,
                requireContext()
            )
        }

        binding.buttonCloseConnector.setOnClickListener {
            wp35 = checkedImageButton(
                it,
                binding.buttonOkConnector,
                binding.buttonCloseConnector,
                requireContext()
            )
        }
    }

    fun setSettingsTestConnector(testConnectorWrapper: CheckingSettingsCustomAdapter.TestConnectorWrapper) {
        testConnectorWrapper.checkingGold.wp35?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOkConnector,
                buttonClose = binding.buttonCloseConnector,
                context = requireContext()
            )
        }
        binding.editTextConnector.setText(testConnectorWrapper.checkingGold.note)
    }

    fun clearView() {
        with(binding) {
            buttonOkConnector.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkConnector.isActivated = false
            buttonCloseConnector.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseConnector.isActivated = false
            editTextConnector.text.clear()
            wp35 = null
        }
    }

    fun onClickSendServer() {
        wp35 = when {
            binding.buttonOkConnector.isActivated -> true
            binding.buttonCloseConnector.isActivated -> false
            else -> null
        }
        viewModel.convertConnectorTestResultToJson(wp35, binding.editTextConnector.text.toString())
    }
}