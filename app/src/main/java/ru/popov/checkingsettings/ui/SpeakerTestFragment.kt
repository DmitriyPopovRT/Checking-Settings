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

    private var wp111: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOk111.setOnClickListener {
            wp111 = Utils.checkedImageButton(
                it,
                binding.buttonOk111,
                binding.buttonClose111,
                requireContext()
            )
        }

        binding.buttonClose111.setOnClickListener {
            wp111 = Utils.checkedImageButton(
                it,
                binding.buttonOk111,
                binding.buttonClose111,
                requireContext()
            )
        }
    }

    fun setSettingsSpeakerTest(speakerTest: CheckingSettingsCustomAdapter.SpeakerTestWrapper) {
        speakerTest.checkSoundSignalWhenSpeakerConnected.wp111?.let {
            Utils.setCheckedImageButtonDownloadServer(
                isChecked = it,
                buttonOk = binding.buttonOk111,
                buttonClose = binding.buttonClose111,
                context = requireContext()
            )
        }
        binding.editTextModSettingSuccess.setText(speakerTest.checkSoundSignalWhenSpeakerConnected.note)
    }

    fun clearView() {
        with(binding) {
            buttonOk111.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.green_100)
            buttonOk111.isActivated = false
            buttonClose111.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.red_100)
            buttonClose111.isActivated = false
            editTextModSettingSuccess.text.clear()
            wp111 = null
        }
    }

    fun onClickSendServer() {
        wp111 = when {
            binding.buttonOk111.isActivated -> true
            binding.buttonClose111.isActivated -> false
            else -> null
        }
        viewModel.convertSpeakerTestResultToJson(
            wp111,
            binding.editTextModSettingSuccess.text.toString()
        )
    }
}