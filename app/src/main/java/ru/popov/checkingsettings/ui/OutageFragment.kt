package ru.popov.checkingsettings.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentOutageBinding
import ru.popov.checkingsettings.databinding.FragmentPackageBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils
import ru.popov.checkingsettings.utils.Utils.checkedImageButton

class OutageFragment : Fragment(R.layout.fragment_outage) {

    private val binding: FragmentOutageBinding by viewBinding(FragmentOutageBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var outage: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOkOutage.setOnClickListener {
            outage = checkedImageButton(
                it,
                binding.buttonOkOutage,
                binding.buttonCloseOutage,
                requireContext()
            )
        }

        binding.buttonCloseOutage.setOnClickListener {
            outage = checkedImageButton(
                it,
                binding.buttonOkOutage,
                binding.buttonCloseOutage,
                requireContext()
            )
        }
    }

    fun setSettingsOutage(outageWrapper: CheckingSettingsCustomAdapter.OutageWrapper) {
        outageWrapper.checkingOutage.outage?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOkOutage,
                buttonClose = binding.buttonCloseOutage,
                context = requireContext()
            )
        }
        binding.editTextOutage.setText(outageWrapper.checkingOutage.note)
    }

    fun clearView() {
        with(binding) {
            buttonOkOutage.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkOutage.isActivated = false
            buttonCloseOutage.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseOutage.isActivated = false
            editTextOutage.text.clear()
            outage = null
        }
    }

    fun onClickSendServer() {
        outage = when {
            binding.buttonOkOutage.isActivated -> true
            binding.buttonCloseOutage.isActivated -> false
            else -> null
        }
        viewModel.convertOutageResultToJson(outage, binding.editTextOutage.text.toString())
    }
}