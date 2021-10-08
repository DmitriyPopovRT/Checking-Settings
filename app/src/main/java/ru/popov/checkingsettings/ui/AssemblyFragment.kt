package ru.popov.checkingsettings.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentAssemblyBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel

class AssemblyFragment : Fragment(R.layout.fragment_assembly) {

    private val binding: FragmentAssemblyBinding by viewBinding(FragmentAssemblyBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    fun clearView() {
        with(binding) {
            editEB12.text.clear()
            editEB13.text.clear()
            editEB14.text.clear()
            editEB15.text.clear()
            editEB16.text.clear()
            editEB17.text.clear()
            editEB18.text.clear()
            editTextModEB.text.clear()
            editBip12.text.clear()
            editBip13.text.clear()
            editBip14.text.clear()
            editBip15.text.clear()
            editBip16.text.clear()
            editBip17.text.clear()
            editBip18.text.clear()
            editTextModBip.text.clear()
            editSpeaker12.text.clear()
            editSpeaker13.text.clear()
            editSpeaker14.text.clear()
            editSpeaker15.text.clear()
            editSpeaker16.text.clear()
            editSpeaker17.text.clear()
            editSpeaker18.text.clear()
            editTextModSpeaker.text.clear()
            editSolderingTemperature12.text.clear()
            editSolderingTemperature13.text.clear()
            editSolderingTemperature14.text.clear()
            editSolderingTemperature15.text.clear()
            editSolderingTemperature16.text.clear()
            editSolderingTemperature17.text.clear()
            editSolderingTemperature18.text.clear()
            editTextModSolderingTemperature.text.clear()
        }
    }

    fun setSettingsAssembly(assembly: CheckingSettingsCustomAdapter.AssemblyWrapper) {
        assembly.assemblyEB.let { assemblyEB ->
            binding.editEB12.setText(assemblyEB.wp12)
            binding.editEB13.setText(assemblyEB.wp13)
            binding.editEB14.setText(assemblyEB.wp14)
            binding.editEB15.setText(assemblyEB.wp15)
            binding.editEB16.setText(assemblyEB.wp16)
            binding.editEB17.setText(assemblyEB.wp17)
            binding.editEB18.setText(assemblyEB.wp18)
            binding.editTextModEB.setText(assemblyEB.note)
        }
        assembly.assemblyBIP.let { assemblyBip ->
            binding.editBip12.setText(assemblyBip.wp12)
            binding.editBip13.setText(assemblyBip.wp13)
            binding.editBip14.setText(assemblyBip.wp14)
            binding.editBip15.setText(assemblyBip.wp15)
            binding.editBip16.setText(assemblyBip.wp16)
            binding.editBip17.setText(assemblyBip.wp17)
            binding.editBip18.setText(assemblyBip.wp18)
            binding.editTextModBip.setText(assemblyBip.note)
        }
        assembly.assemblySpeaker.let { assemblySpeaker ->
            binding.editSpeaker12.setText(assemblySpeaker.wp12)
            binding.editSpeaker13.setText(assemblySpeaker.wp13)
            binding.editSpeaker14.setText(assemblySpeaker.wp14)
            binding.editSpeaker15.setText(assemblySpeaker.wp15)
            binding.editSpeaker16.setText(assemblySpeaker.wp16)
            binding.editSpeaker17.setText(assemblySpeaker.wp17)
            binding.editSpeaker18.setText(assemblySpeaker.wp18)
            binding.editTextModSpeaker.setText(assemblySpeaker.note)
        }
        assembly.solderingTemperature.let { solderingTemperature ->
            binding.editSolderingTemperature12.setText(solderingTemperature.wp12)
            binding.editSolderingTemperature13.setText(solderingTemperature.wp13)
            binding.editSolderingTemperature14.setText(solderingTemperature.wp14)
            binding.editSolderingTemperature15.setText(solderingTemperature.wp15)
            binding.editSolderingTemperature16.setText(solderingTemperature.wp16)
            binding.editSolderingTemperature17.setText(solderingTemperature.wp17)
            binding.editSolderingTemperature18.setText(solderingTemperature.wp18)
            binding.editTextModSolderingTemperature.setText(solderingTemperature.note)
        }
    }

    fun onClickSendServer() {
        with(binding) {
            viewModel.convertAssemblyResultToJson(
                editEB12.text.toString(),
                editEB13.text.toString(),
                editEB14.text.toString(),
                editEB15.text.toString(),
                editEB16.text.toString(),
                editEB17.text.toString(),
                editEB18.text.toString(),
                editTextModEB.text.toString(),
                editBip12.text.toString(),
                editBip13.text.toString(),
                editBip14.text.toString(),
                editBip15.text.toString(),
                editBip16.text.toString(),
                editBip17.text.toString(),
                editBip18.text.toString(),
                editTextModBip.text.toString(),
                editSpeaker12.text.toString(),
                editSpeaker13.text.toString(),
                editSpeaker14.text.toString(),
                editSpeaker15.text.toString(),
                editSpeaker16.text.toString(),
                editSpeaker17.text.toString(),
                editSpeaker18.text.toString(),
                editTextModSpeaker.text.toString(),
                editSolderingTemperature12.text.toString(),
                editSolderingTemperature13.text.toString(),
                editSolderingTemperature14.text.toString(),
                editSolderingTemperature15.text.toString(),
                editSolderingTemperature16.text.toString(),
                editSolderingTemperature17.text.toString(),
                editSolderingTemperature18.text.toString(),
                editTextModSolderingTemperature.text.toString(),
            )
        }
    }

}