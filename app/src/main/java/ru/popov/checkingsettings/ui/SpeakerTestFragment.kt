package ru.popov.checkingsettings.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentSpeakerTestBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils

class SpeakerTestFragment : Fragment(R.layout.fragment_speaker_test) {

    private val binding: FragmentSpeakerTestBinding by viewBinding(
        FragmentSpeakerTestBinding::bind
    )
    private val viewModel: HomeViewModel by viewModels()

    private var wp25: Boolean? = null
    private var wp26: Boolean? = null
    private var wpGold25: Boolean? = null
    private var wpGold26: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOk25.setOnClickListener {
            wp25 = Utils.checkedImageButton(
                it,
                binding.buttonOk25,
                binding.buttonClose25,
                requireContext()
            )
        }
        binding.buttonClose25.setOnClickListener {
            wp25 = Utils.checkedImageButton(
                it,
                binding.buttonOk25,
                binding.buttonClose25,
                requireContext()
            )
        }

        binding.buttonOk26.setOnClickListener {
            wp26 = Utils.checkedImageButton(
                it,
                binding.buttonOk26,
                binding.buttonClose26,
                requireContext()
            )
        }
        binding.buttonClose26.setOnClickListener {
            wp26 = Utils.checkedImageButton(
                it,
                binding.buttonOk26,
                binding.buttonClose26,
                requireContext()
            )
        }

        binding.buttonOkGold25.setOnClickListener {
            wpGold25 = Utils.checkedImageButton(
                it,
                binding.buttonOkGold25,
                binding.buttonCloseGold25,
                requireContext()
            )
        }
        binding.buttonCloseGold25.setOnClickListener {
            wpGold25 = Utils.checkedImageButton(
                it,
                binding.buttonOkGold25,
                binding.buttonCloseGold25,
                requireContext()
            )
        }

        binding.buttonOkGold26.setOnClickListener {
            wpGold26 = Utils.checkedImageButton(
                it,
                binding.buttonOkGold26,
                binding.buttonCloseGold26,
                requireContext()
            )
        }
        binding.buttonCloseGold26.setOnClickListener {
            wpGold26 = Utils.checkedImageButton(
                it,
                binding.buttonOkGold26,
                binding.buttonCloseGold26,
                requireContext()
            )
        }
    }

    fun setSettingsSpeakerTest(speakerTest: CheckingSettingsCustomAdapter.SpeakerTestWrapper) {
        speakerTest.checkSoundSignalWhenSpeakerConnected.wp25?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk25,
                buttonClose = binding.buttonClose25,
                context = requireContext()
            )
        }
        speakerTest.checkSoundSignalWhenSpeakerConnected.wp26?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk26,
                buttonClose = binding.buttonClose26,
                context = requireContext()
            )
        }
        speakerTest.checkSoundSignalWhenSpeakerConnected.wpGold25?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOkGold25,
                buttonClose = binding.buttonCloseGold25,
                context = requireContext()
            )
        }
        speakerTest.checkSoundSignalWhenSpeakerConnected.wpGold26?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOkGold26,
                buttonClose = binding.buttonCloseGold26,
                context = requireContext()
            )
        }
        binding.editTextModSettingSuccess.setText(speakerTest.checkSoundSignalWhenSpeakerConnected.note)
        binding.editTextModGold.setText(speakerTest.checkSoundSignalWhenSpeakerConnected.noteGold)
    }

    fun clearView() {
        with(binding) {
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

            buttonOkGold25.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkGold25.isActivated = false
            buttonCloseGold25.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseGold25.isActivated = false

            buttonOkGold26.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOkGold26.isActivated = false
            buttonCloseGold26.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonCloseGold26.isActivated = false

            editTextModSettingSuccess.text.clear()
            editTextModGold.text.clear()

            wp25 = null
            wp26 = null
            wpGold25 = null
            wpGold26 = null
        }
    }

    fun onClickSendServer() {
        wp25 = when {
            binding.buttonOk25.isActivated -> true
            binding.buttonClose25.isActivated -> false
            else -> null
        }
        wp26 = when {
            binding.buttonOk26.isActivated -> true
            binding.buttonClose26.isActivated -> false
            else -> null
        }
        wpGold25 = when {
            binding.buttonOkGold25.isActivated -> true
            binding.buttonCloseGold25.isActivated -> false
            else -> null
        }
        wpGold26 = when {
            binding.buttonOkGold26.isActivated -> true
            binding.buttonCloseGold26.isActivated -> false
            else -> null
        }
        viewModel.convertSpeakerTestResultToJson(
            wp25,
            wp26,
            wpGold25,
            wpGold26,
            binding.editTextModSettingSuccess.text.toString(),
            binding.editTextModGold.text.toString()
        )
    }
}