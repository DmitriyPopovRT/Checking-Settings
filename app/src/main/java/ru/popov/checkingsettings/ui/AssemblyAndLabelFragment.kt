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

    private var wp33: Boolean? = null
    private var wp34: Boolean? = null
    private var wp36: Boolean? = null
    private var wp37: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOk33.setOnClickListener {
            wp33 = Utils.checkedImageButton(
                it,
                binding.buttonOk33,
                binding.buttonClose33,
                requireContext()
            )
        }
        binding.buttonClose33.setOnClickListener {
            wp33 = Utils.checkedImageButton(
                it,
                binding.buttonOk33,
                binding.buttonClose33,
                requireContext()
            )
        }

        binding.buttonOk34.setOnClickListener {
            wp34 = Utils.checkedImageButton(
                it,
                binding.buttonOk34,
                binding.buttonClose34,
                requireContext()
            )
        }
        binding.buttonClose34.setOnClickListener {
            wp34 = Utils.checkedImageButton(
                it,
                binding.buttonOk34,
                binding.buttonClose34,
                requireContext()
            )
        }

        binding.buttonOk36.setOnClickListener {
            wp36 = Utils.checkedImageButton(
                it,
                binding.buttonOk36,
                binding.buttonClose36,
                requireContext()
            )
        }
        binding.buttonClose36.setOnClickListener {
            wp36 = Utils.checkedImageButton(
                it,
                binding.buttonOk36,
                binding.buttonClose36,
                requireContext()
            )
        }

        binding.buttonOk37.setOnClickListener {
            wp37 = Utils.checkedImageButton(
                it,
                binding.buttonOk37,
                binding.buttonClose37,
                requireContext()
            )
        }
        binding.buttonClose37.setOnClickListener {
            wp37 = Utils.checkedImageButton(
                it,
                binding.buttonOk37,
                binding.buttonClose37,
                requireContext()
            )
        }
    }

    fun setSettingsAssemblyAndLabel(assemblyAndLabel: CheckingSettingsCustomAdapter.AssemblyAndLabelWrapper) {
        assemblyAndLabel.scanningLabelNumberNotFound.wp33?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk33,
                buttonClose = binding.buttonClose33,
                context = requireContext()
            )
        }
        assemblyAndLabel.scanningLabelNumberNotFound.wp34?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk34,
                buttonClose = binding.buttonClose34,
                context = requireContext()
            )
        }
        assemblyAndLabel.scanningLabelNumberNotFound.wp36?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk36,
                buttonClose = binding.buttonClose36,
                context = requireContext()
            )
        }
        assemblyAndLabel.scanningLabelNumberNotFound.wp37?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk37,
                buttonClose = binding.buttonClose37,
                context = requireContext()
            )
        }
        binding.editTextMod2.setText(assemblyAndLabel.scanningLabelNumberNotFound.note)
    }

    fun clearView() {
        with(binding) {
            buttonOk33.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk33.isActivated = false
            buttonClose33.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose33.isActivated = false

            buttonOk34.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk34.isActivated = false
            buttonClose34.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose34.isActivated = false

            buttonOk36.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk36.isActivated = false
            buttonClose36.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose36.isActivated = false

            buttonOk37.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk37.isActivated = false
            buttonClose37.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose37.isActivated = false

            editTextMod2.text.clear()
            wp33 = null
            wp34 = null
            wp36 = null
            wp37 = null
        }
    }

    fun onClickSendServer() {
        wp33 = when {
            binding.buttonOk33.isActivated -> true
            binding.buttonClose33.isActivated -> false
            else -> null
        }
        wp34 = when {
            binding.buttonOk34.isActivated -> true
            binding.buttonClose34.isActivated -> false
            else -> null
        }
        wp36 = when {
            binding.buttonOk36.isActivated -> true
            binding.buttonClose36.isActivated -> false
            else -> null
        }
        wp37 = when {
            binding.buttonOk37.isActivated -> true
            binding.buttonClose37.isActivated -> false
            else -> null
        }
        viewModel.convertAssemblyAndLabelResultToJson(wp33, wp34, wp36, wp37, binding.editTextMod2.text.toString())
    }
}