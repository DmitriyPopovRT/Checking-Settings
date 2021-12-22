package ru.popov.checkingsettings.ui

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter.ValueAndCalibration
import ru.popov.checkingsettings.databinding.FragmentAssemblyBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel

class AssemblyFragment : Fragment(R.layout.fragment_assembly) {

    private val binding: FragmentAssemblyBinding by viewBinding(FragmentAssemblyBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    fun clearView() {
        with(binding) {
            editEB11.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB12.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB13.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB14.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB15.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB16.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB17.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB18.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editTextModEB.text.clear()
            editBip11.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editBip12.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editBip13.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editBip14.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editBip15.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editBip16.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editBip17.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editBip18.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editTextModBip.text.clear()
            editSpeaker11.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSpeaker12.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSpeaker13.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSpeaker14.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSpeaker15.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSpeaker16.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSpeaker17.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSpeaker18.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editTextModSpeaker.text.clear()
            editSolderingTemperature11.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSolderingTemperature12.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSolderingTemperature13.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSolderingTemperature14.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSolderingTemperature15.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSolderingTemperature16.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSolderingTemperature17.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSolderingTemperature18.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editTextModSolderingTemperature.text.clear()
            editFixing23.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editFixing24.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editFixing25.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editFixing26.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editFixing27.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editFixing28.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            noteAssemblyFixing.text.clear()
        }
    }

    fun setSettingsAssembly(assembly: CheckingSettingsCustomAdapter.AssemblyWrapper) {
        assembly.assemblyEB.let { assemblyEB ->
            binding.editEB11.apply {
                setText(assemblyEB.wp11?.value)
                if (assemblyEB.wp11?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB12.apply {
                setText(assemblyEB.wp12?.value)
                if (assemblyEB.wp12?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB13.apply {
                setText(assemblyEB.wp13?.value)
                if (assemblyEB.wp13?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB14.apply {
                setText(assemblyEB.wp14?.value)
                if (assemblyEB.wp14?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB15.apply {
                setText(assemblyEB.wp15?.value)
                if (assemblyEB.wp15?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB16.apply {
                setText(assemblyEB.wp16?.value)
                if (assemblyEB.wp16?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB17.apply {
                setText(assemblyEB.wp17?.value)
                if (assemblyEB.wp17?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB18.apply {
                setText(assemblyEB.wp18?.value)
                if (assemblyEB.wp18?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editTextModEB.setText(assemblyEB.note)
        }
        assembly.assemblyBIP.let { assemblyBip ->
            binding.editBip11.apply {
                setText(assemblyBip.wp11?.value)
                if (assemblyBip.wp11?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editBip12.apply {
                setText(assemblyBip.wp12?.value)
                if (assemblyBip.wp12?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editBip13.apply {
                setText(assemblyBip.wp13?.value)
                if (assemblyBip.wp13?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editBip14.apply {
                setText(assemblyBip.wp14?.value)
                if (assemblyBip.wp14?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editBip15.apply {
                setText(assemblyBip.wp15?.value)
                if (assemblyBip.wp15?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editBip16.apply {
                setText(assemblyBip.wp16?.value)
                if (assemblyBip.wp16?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editBip17.apply {
                setText(assemblyBip.wp17?.value)
                if (assemblyBip.wp17?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editBip18.apply {
                setText(assemblyBip.wp18?.value)
                if (assemblyBip.wp18?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editTextModBip.setText(assemblyBip.note)
        }
        assembly.assemblySpeaker.let { assemblySpeaker ->
            binding.editSpeaker11.apply {
                setText(assemblySpeaker.wp11?.value)
                if (assemblySpeaker.wp11?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSpeaker12.apply {
                setText(assemblySpeaker.wp12?.value)
                if (assemblySpeaker.wp12?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSpeaker13.apply {
                setText(assemblySpeaker.wp13?.value)
                if (assemblySpeaker.wp13?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSpeaker14.apply {
                setText(assemblySpeaker.wp14?.value)
                if (assemblySpeaker.wp14?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSpeaker15.apply {
                setText(assemblySpeaker.wp15?.value)
                if (assemblySpeaker.wp15?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSpeaker16.apply {
                setText(assemblySpeaker.wp16?.value)
                if (assemblySpeaker.wp16?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSpeaker17.apply {
                setText(assemblySpeaker.wp17?.value)
                if (assemblySpeaker.wp17?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSpeaker18.apply {
                setText(assemblySpeaker.wp18?.value)
                if (assemblySpeaker.wp18?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editTextModSpeaker.setText(assemblySpeaker.note)
        }
        assembly.solderingTemperature.let { solderingTemperature ->
            binding.editSolderingTemperature11.apply {
                setText(solderingTemperature.wp11?.value)
                if (solderingTemperature.wp11?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSolderingTemperature12.apply {
                setText(solderingTemperature.wp12?.value)
                if (solderingTemperature.wp12?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSolderingTemperature13.apply {
                setText(solderingTemperature.wp13?.value)
                if (solderingTemperature.wp13?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSolderingTemperature14.apply {
                setText(solderingTemperature.wp14?.value)
                if (solderingTemperature.wp14?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSolderingTemperature15.apply {
                setText(solderingTemperature.wp15?.value)
                if (solderingTemperature.wp15?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSolderingTemperature16.apply {
                setText(solderingTemperature.wp16?.value)
                if (solderingTemperature.wp16?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSolderingTemperature17.apply {
                setText(solderingTemperature.wp17?.value)
                if (solderingTemperature.wp17?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSolderingTemperature18.apply {
                setText(solderingTemperature.wp18?.value)
                if (solderingTemperature.wp18?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editTextModSolderingTemperature.setText(solderingTemperature.note)
        }
        assembly.assemblyFixing?.let { assemblyFixing ->
            binding.editFixing23.apply {
                setText(assemblyFixing.wp23?.value)
                if (assemblyFixing.wp23?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editFixing24.apply {
                setText(assemblyFixing.wp24?.value)
                if (assemblyFixing.wp24?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editFixing25.apply {
                setText(assemblyFixing.wp25?.value)
                if (assemblyFixing.wp25?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editFixing26.apply {
                setText(assemblyFixing.wp26?.value)
                if (assemblyFixing.wp26?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editFixing27.apply {
                setText(assemblyFixing.wp27?.value)
                if (assemblyFixing.wp27?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editFixing28.apply {
                setText(assemblyFixing.wp28?.value)
                if (assemblyFixing.wp28?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.noteAssemblyFixing.setText(assemblyFixing.note)
        }
    }

    fun onClickSendServer() {
        with(binding) {
            viewModel.convertAssemblyResultToJson(
                ValueAndCalibration(editEB11.text.toString(), editEB11.isActivated),
                ValueAndCalibration(editEB12.text.toString(), editEB12.isActivated),
                ValueAndCalibration(editEB13.text.toString(), editEB13.isActivated),
                ValueAndCalibration(editEB14.text.toString(), editEB14.isActivated),
                ValueAndCalibration(editEB15.text.toString(), editEB15.isActivated),
                ValueAndCalibration(editEB16.text.toString(), editEB16.isActivated),
                ValueAndCalibration(editEB17.text.toString(), editEB17.isActivated),
                ValueAndCalibration(editEB18.text.toString(), editEB18.isActivated),
                editTextModEB.text.toString(),
                ValueAndCalibration(editBip11.text.toString(), editBip11.isActivated),
                ValueAndCalibration(editBip12.text.toString(), editBip12.isActivated),
                ValueAndCalibration(editBip13.text.toString(), editBip13.isActivated),
                ValueAndCalibration(editBip14.text.toString(), editBip14.isActivated),
                ValueAndCalibration(editBip15.text.toString(), editBip15.isActivated),
                ValueAndCalibration(editBip16.text.toString(), editBip16.isActivated),
                ValueAndCalibration(editBip17.text.toString(), editBip17.isActivated),
                ValueAndCalibration(editBip18.text.toString(), editBip18.isActivated),
                editTextModBip.text.toString(),
                ValueAndCalibration(editSpeaker11.text.toString(), editSpeaker11.isActivated),
                ValueAndCalibration(editSpeaker12.text.toString(), editSpeaker12.isActivated),
                ValueAndCalibration(editSpeaker13.text.toString(), editSpeaker13.isActivated),
                ValueAndCalibration(editSpeaker14.text.toString(), editSpeaker14.isActivated),
                ValueAndCalibration(editSpeaker15.text.toString(), editSpeaker15.isActivated),
                ValueAndCalibration(editSpeaker16.text.toString(), editSpeaker16.isActivated),
                ValueAndCalibration(editSpeaker17.text.toString(), editSpeaker17.isActivated),
                ValueAndCalibration(editSpeaker18.text.toString(), editSpeaker18.isActivated),
                editTextModSpeaker.text.toString(),
                ValueAndCalibration(
                    editSolderingTemperature11.text.toString(),
                    editSolderingTemperature11.isActivated
                ),
                ValueAndCalibration(
                    editSolderingTemperature12.text.toString(),
                    editSolderingTemperature12.isActivated
                ),
                ValueAndCalibration(
                    editSolderingTemperature13.text.toString(),
                    editSolderingTemperature13.isActivated
                ),
                ValueAndCalibration(
                    editSolderingTemperature14.text.toString(),
                    editSolderingTemperature14.isActivated
                ),
                ValueAndCalibration(
                    editSolderingTemperature15.text.toString(),
                    editSolderingTemperature15.isActivated
                ),
                ValueAndCalibration(
                    editSolderingTemperature16.text.toString(),
                    editSolderingTemperature16.isActivated
                ),
                ValueAndCalibration(
                    editSolderingTemperature17.text.toString(),
                    editSolderingTemperature17.isActivated
                ),
                ValueAndCalibration(
                    editSolderingTemperature18.text.toString(),
                    editSolderingTemperature18.isActivated
                ),
                editTextModSolderingTemperature.text.toString(),
                ValueAndCalibration(editFixing23.text.toString(), editFixing23.isActivated),
                ValueAndCalibration(editFixing24.text.toString(), editFixing24.isActivated),
                ValueAndCalibration(editFixing25.text.toString(), editFixing25.isActivated),
                ValueAndCalibration(editFixing26.text.toString(), editFixing26.isActivated),
                ValueAndCalibration(editFixing27.text.toString(), editFixing27.isActivated),
                ValueAndCalibration(editFixing28.text.toString(), editFixing28.isActivated),
                noteAssemblyFixing.text.toString()
            )
        }
    }

    private fun customEditBack(view: View): Boolean {
        if (view.isActivated) {
//                    it.setBackgroundColor(Color.GREEN)
            view.setBackgroundColor(Color.WHITE)
            view.isActivated = false
        } else {
//            toast("editEB11")
            view.setBackgroundColor(resources.getColor(R.color.blue_300))
//                    it.setBackgroundColor(Color.RED)
            view.isActivated = true
        }
        return true
    }

    fun onLongClickEditText() {
        with(binding) {
            editEB11.setOnLongClickListener { customEditBack(it) }
            editEB12.setOnLongClickListener { customEditBack(it) }
            editEB13.setOnLongClickListener { customEditBack(it) }
            editEB14.setOnLongClickListener { customEditBack(it) }
            editEB15.setOnLongClickListener { customEditBack(it) }
            editEB16.setOnLongClickListener { customEditBack(it) }
            editEB17.setOnLongClickListener { customEditBack(it) }
            editEB18.setOnLongClickListener { customEditBack(it) }
            editBip11.setOnLongClickListener { customEditBack(it) }
            editBip12.setOnLongClickListener { customEditBack(it) }
            editBip13.setOnLongClickListener { customEditBack(it) }
            editBip14.setOnLongClickListener { customEditBack(it) }
            editBip15.setOnLongClickListener { customEditBack(it) }
            editBip16.setOnLongClickListener { customEditBack(it) }
            editBip17.setOnLongClickListener { customEditBack(it) }
            editBip18.setOnLongClickListener { customEditBack(it) }
            editSpeaker11.setOnLongClickListener { customEditBack(it) }
            editSpeaker12.setOnLongClickListener { customEditBack(it) }
            editSpeaker13.setOnLongClickListener { customEditBack(it) }
            editSpeaker14.setOnLongClickListener { customEditBack(it) }
            editSpeaker15.setOnLongClickListener { customEditBack(it) }
            editSpeaker16.setOnLongClickListener { customEditBack(it) }
            editSpeaker17.setOnLongClickListener { customEditBack(it) }
            editSpeaker18.setOnLongClickListener { customEditBack(it) }
            editSolderingTemperature11.setOnLongClickListener { customEditBack(it) }
            editSolderingTemperature12.setOnLongClickListener { customEditBack(it) }
            editSolderingTemperature13.setOnLongClickListener { customEditBack(it) }
            editSolderingTemperature14.setOnLongClickListener { customEditBack(it) }
            editSolderingTemperature15.setOnLongClickListener { customEditBack(it) }
            editSolderingTemperature16.setOnLongClickListener { customEditBack(it) }
            editSolderingTemperature17.setOnLongClickListener { customEditBack(it) }
            editSolderingTemperature18.setOnLongClickListener { customEditBack(it) }
            editFixing23.setOnLongClickListener { customEditBack(it) }
            editFixing24.setOnLongClickListener { customEditBack(it) }
            editFixing25.setOnLongClickListener { customEditBack(it) }
            editFixing26.setOnLongClickListener { customEditBack(it) }
            editFixing27.setOnLongClickListener { customEditBack(it) }
            editFixing28.setOnLongClickListener { customEditBack(it) }
        }
    }

}