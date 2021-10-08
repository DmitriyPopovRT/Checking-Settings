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
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils
import ru.popov.checkingsettings.utils.Utils.checkedImageButton

class PackageFragment : Fragment(R.layout.fragment_package) {

    private val binding: FragmentPackageBinding by viewBinding(FragmentPackageBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var wp29: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOk.setOnClickListener {
            wp29 = checkedImageButton(
                it,
                binding.buttonOk,
                binding.buttonClose,
                requireContext()
            )
        }

        binding.buttonClose.setOnClickListener {
            wp29 = checkedImageButton(
                it,
                binding.buttonOk,
                binding.buttonClose,
                requireContext()
            )
        }
    }

    fun setSettingsPackage(packageWrapper: CheckingSettingsCustomAdapter.PackageWrapper) {
        packageWrapper.checkingPackagedProduct.wp29?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk,
                buttonClose = binding.buttonClose,
                context = requireContext()
            )
        }
        binding.editText.setText(packageWrapper.checkingPackagedProduct.note)
    }

    fun clearView() {
        with(binding) {
            buttonOk.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk.isActivated = false
            buttonClose.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose.isActivated = false
            editText.text.clear()
            wp29 = null
        }
    }

    fun onClickSendServer() {
        wp29 = when {
            binding.buttonOk.isActivated -> true
            binding.buttonClose.isActivated -> false
            else -> null
        }
        viewModel.convertPackageResultToJson(wp29, binding.editText.text.toString())
    }
}