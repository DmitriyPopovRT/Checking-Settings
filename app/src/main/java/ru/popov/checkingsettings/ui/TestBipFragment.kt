package ru.popov.checkingsettings.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentBipTestBinding
import ru.popov.checkingsettings.databinding.FragmentSpeakerTestBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils

class TestBipFragment : Fragment(R.layout.fragment_bip_test) {

    private val binding: FragmentBipTestBinding by viewBinding(
        FragmentBipTestBinding::bind
    )
    private val viewModel: HomeViewModel by viewModels()

    private var wp17: Boolean? = null
    private var wp18: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOk17.setOnClickListener {
            wp17 = Utils.checkedImageButton(
                it,
                binding.buttonOk17,
                binding.buttonClose17,
                requireContext()
            )
        }
        binding.buttonClose17.setOnClickListener {
            wp17 = Utils.checkedImageButton(
                it,
                binding.buttonOk17,
                binding.buttonClose17,
                requireContext()
            )
        }

        binding.buttonOk18.setOnClickListener {
            wp18 = Utils.checkedImageButton(
                it,
                binding.buttonOk18,
                binding.buttonClose18,
                requireContext()
            )
        }
        binding.buttonClose18.setOnClickListener {
            wp18 = Utils.checkedImageButton(
                it,
                binding.buttonOk18,
                binding.buttonClose18,
                requireContext()
            )
        }
    }

    fun setSettingsBipTest(bipTest: CheckingSettingsCustomAdapter.BipTestWrapper) {
        bipTest.goldTest.wp17?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk17,
                buttonClose = binding.buttonClose17,
                context = requireContext()
            )
        }
        bipTest.goldTest.wp18?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk18,
                buttonClose = binding.buttonClose18,
                context = requireContext()
            )
        }
        binding.editTextModBip.setText(bipTest.goldTest.note)
    }

    fun clearView() {
        with(binding) {
            buttonOk17.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk17.isActivated = false
            buttonClose17.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose17.isActivated = false

            buttonOk18.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk18.isActivated = false
            buttonClose18.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose18.isActivated = false

            editTextModBip.text.clear()

            wp17 = null
            wp18 = null
        }
    }

    fun onClickSendServer() {
        wp17 = when {
            binding.buttonOk17.isActivated -> true
            binding.buttonClose17.isActivated -> false
            else -> null
        }
        wp18 = when {
            binding.buttonOk18.isActivated -> true
            binding.buttonClose18.isActivated -> false
            else -> null
        }
        viewModel.convertBipTestResultToJson(
            wp17,
            wp18,
            binding.editTextModBip.text.toString()
        )
    }
}