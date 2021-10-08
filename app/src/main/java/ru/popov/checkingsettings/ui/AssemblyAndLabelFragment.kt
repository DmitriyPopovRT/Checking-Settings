package ru.popov.checkingsettings.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentAssemblyAndLabelBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils

class AssemblyAndLabelFragment : Fragment(R.layout.fragment_assembly_and_label) {

    private val binding: FragmentAssemblyAndLabelBinding by viewBinding(
        FragmentAssemblyAndLabelBinding::bind
    )
    private val viewModel: HomeViewModel by viewModels()

    private var wp28: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOk28.setOnClickListener {
            wp28 = Utils.checkedImageButton(
                it,
                binding.buttonOk28,
                binding.buttonClose28,
                requireContext()
            )
        }

        binding.buttonClose28.setOnClickListener {
            wp28 = Utils.checkedImageButton(
                it,
                binding.buttonOk28,
                binding.buttonClose28,
                requireContext()
            )
        }
    }

    fun setSettingsAssemblyAndLabel(assemblyAndLabel: CheckingSettingsCustomAdapter.AssemblyAndLabelWrapper) {
        assemblyAndLabel.scanningLabelNumberNotFound.wp28?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk28,
                buttonClose = binding.buttonClose28,
                context = requireContext()
            )
        }
        binding.editTextMod2.setText(assemblyAndLabel.scanningLabelNumberNotFound.note)
    }

    fun clearView() {
        with(binding) {
            buttonOk28.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk28.isActivated = false
            buttonClose28.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose28.isActivated = false
            editTextMod2.text.clear()
            wp28 = null
        }
    }

    fun onClickSendServer() {
        wp28 = when {
            binding.buttonOk28.isActivated -> true
            binding.buttonClose28.isActivated -> false
            else -> null
        }
        viewModel.convertAssemblyAndLabelResultToJson(wp28, binding.editTextMod2.text.toString())
    }
}