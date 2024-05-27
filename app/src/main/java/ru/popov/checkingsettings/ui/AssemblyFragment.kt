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
            editEB07.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB08.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB09.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editEB10.apply {
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
            noteAssemblyBip.text.clear()
            editSoldering01.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering02.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering03.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering04.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering05.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering19.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering20.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering21.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering22.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering23.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editSoldering24.apply {
                text.clear()
                setBackgroundColor(Color.WHITE)
            }
            editTextModSolderingTemperature.text.clear()
        }
    }

    fun setSettingsAssembly(assembly: CheckingSettingsCustomAdapter.AssemblyWrapper) {
        assembly.assemblyEB.let { assemblyEB ->
            binding.editEB07.apply {
                setText(assemblyEB.wp07?.value)
                if (assemblyEB.wp07?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB08.apply {
                setText(assemblyEB.wp08?.value)
                if (assemblyEB.wp08?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB09.apply {
                setText(assemblyEB.wp09?.value)
                if (assemblyEB.wp09?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editEB10.apply {
                setText(assemblyEB.wp10?.value)
                if (assemblyEB.wp10?.isCalibration == true) {
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
            binding.noteAssemblyBip.setText(assemblyBip.note)
        }
        assembly.solderingTemperature.let { solderingTemperature ->
            binding.editSoldering01.apply {
                setText(solderingTemperature.wp01?.value)
                if (solderingTemperature.wp01?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering02.apply {
                setText(solderingTemperature.wp02?.value)
                if (solderingTemperature.wp02?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering03.apply {
                setText(solderingTemperature.wp03?.value)
                if (solderingTemperature.wp03?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering04.apply {
                setText(solderingTemperature.wp04?.value)
                if (solderingTemperature.wp04?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering05.apply {
                setText(solderingTemperature.wp05?.value)
                if (solderingTemperature.wp05?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering19.apply {
                setText(solderingTemperature.wp19?.value)
                if (solderingTemperature.wp19?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering20.apply {
                setText(solderingTemperature.wp20?.value)
                if (solderingTemperature.wp20?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering21.apply {
                setText(solderingTemperature.wp21?.value)
                if (solderingTemperature.wp21?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering22.apply {
                setText(solderingTemperature.wp22?.value)
                if (solderingTemperature.wp22?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering23.apply {
                setText(solderingTemperature.wp23?.value)
                if (solderingTemperature.wp23?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editSoldering24.apply {
                setText(solderingTemperature.wp24?.value)
                if (solderingTemperature.wp24?.isCalibration == true) {
                    isActivated = true
                    setBackgroundColor(resources.getColor(R.color.blue_300))
                }
            }
            binding.editTextModSolderingTemperature.setText(solderingTemperature.note)
        }
    }

    fun onClickSendServer() {
        with(binding) {
            viewModel.convertAssemblyResultToJson(
                ValueAndCalibration(editEB07.text.toString(), editEB07.isActivated),
                ValueAndCalibration(editEB08.text.toString(), editEB08.isActivated),
                ValueAndCalibration(editEB09.text.toString(), editEB09.isActivated),
                ValueAndCalibration(editEB10.text.toString(), editEB10.isActivated),
                editTextModEB.text.toString(),
                ValueAndCalibration(editBip11.text.toString(), editBip11.isActivated),
                ValueAndCalibration(editBip12.text.toString(), editBip12.isActivated),
                ValueAndCalibration(editBip13.text.toString(), editBip13.isActivated),
                ValueAndCalibration(editBip14.text.toString(), editBip14.isActivated),
                ValueAndCalibration(editBip15.text.toString(), editBip15.isActivated),
                ValueAndCalibration(editBip16.text.toString(), editBip16.isActivated),
                noteAssemblyBip.text.toString(),
                ValueAndCalibration(editSoldering01.text.toString(), editSoldering01.isActivated),
                ValueAndCalibration(editSoldering02.text.toString(), editSoldering02.isActivated),
                ValueAndCalibration(editSoldering03.text.toString(), editSoldering03.isActivated),
                ValueAndCalibration(editSoldering04.text.toString(), editSoldering04.isActivated),
                ValueAndCalibration(editSoldering05.text.toString(), editSoldering05.isActivated),
                ValueAndCalibration(editSoldering19.text.toString(), editSoldering19.isActivated),
                ValueAndCalibration(editSoldering20.text.toString(), editSoldering20.isActivated),
                ValueAndCalibration(editSoldering21.text.toString(), editSoldering21.isActivated),
                ValueAndCalibration(editSoldering22.text.toString(), editSoldering22.isActivated),
                ValueAndCalibration(editSoldering23.text.toString(), editSoldering23.isActivated),
                ValueAndCalibration(editSoldering24.text.toString(), editSoldering24.isActivated),
                editTextModSolderingTemperature.text.toString()
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
            editEB07.setOnLongClickListener { customEditBack(it) }
            editEB08.setOnLongClickListener { customEditBack(it) }
            editEB09.setOnLongClickListener { customEditBack(it) }
            editEB10.setOnLongClickListener { customEditBack(it) }
            editBip11.setOnLongClickListener { customEditBack(it) }
            editBip12.setOnLongClickListener { customEditBack(it) }
            editBip13.setOnLongClickListener { customEditBack(it) }
            editBip14.setOnLongClickListener { customEditBack(it) }
            editBip15.setOnLongClickListener { customEditBack(it) }
            editBip16.setOnLongClickListener { customEditBack(it) }
            editSoldering01.setOnLongClickListener { customEditBack(it) }
            editSoldering02.setOnLongClickListener { customEditBack(it) }
            editSoldering03.setOnLongClickListener { customEditBack(it) }
            editSoldering04.setOnLongClickListener { customEditBack(it) }
            editSoldering05.setOnLongClickListener { customEditBack(it) }
            editSoldering19.setOnLongClickListener { customEditBack(it) }
            editSoldering20.setOnLongClickListener { customEditBack(it) }
            editSoldering21.setOnLongClickListener { customEditBack(it) }
            editSoldering22.setOnLongClickListener { customEditBack(it) }
            editSoldering23.setOnLongClickListener { customEditBack(it) }
            editSoldering24.setOnLongClickListener { customEditBack(it) }
        }
    }
}