package ru.popov.checkingsettings.ui.home

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.moshi.Moshi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentHomeBinding
import ru.popov.checkingsettings.ui.*
import timber.log.Timber
import java.time.LocalDateTime

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var packageFragment: PackageFragment? = null
    private var programTestFragment: ProgramTestFragment? = null
    private var assemblyAndLabelFragment: AssemblyAndLabelFragment? = null
    private var assemblyFragment: AssemblyFragment? = null
    private var speakerTestFragment: SpeakerTestFragment? = null

    private var selectedDate = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Получаем инстансы фрагментов
        getFragment()

        bindViewModel()

        binding.buttonSendServer.setOnClickListener {
            lifecycleScope.launch {
                showProgressSend(false)
                // Отправляем команды собрать информацию с вью и записать в строки json
                sendSettings()

                // Генерируем общую строку и отправляем на сервер
                viewModel.generateJson()
                showProgressSend(true)
            }
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_load_settings -> {
                    // Получаем дату которую выбрал пользователь
                    getDate()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }

        binding.buttonClear.setOnClickListener {
            // Очищаем все вью
            clearWorkspace()

            binding.constraint.setBackgroundColor(Color.WHITE)
            binding.titleCheckingSettings.isVisible = false
            binding.buttonClear.isVisible = false
            selectedDate = ""
        }
    }

    // Получаем инстансы фрагментов
    private fun getFragment() {
        packageFragment =
            childFragmentManager.findFragmentByTag("fragmentPackage") as PackageFragment?
        programTestFragment =
            childFragmentManager.findFragmentByTag("fragmentProgramTest") as ProgramTestFragment?
        assemblyAndLabelFragment =
            childFragmentManager.findFragmentByTag("fragmentAssemblyAndLabel") as AssemblyAndLabelFragment?
        assemblyFragment =
            childFragmentManager.findFragmentByTag("fragmentAssembly") as AssemblyFragment?
        speakerTestFragment =
            childFragmentManager.findFragmentByTag("fragmentSpeakerTest") as SpeakerTestFragment?
    }

    // Показываем/скрываем progressBar
    private fun showProgressSend(isSending: Boolean) {
        with(binding) {
            buttonSendServer.isEnabled = isSending
            scrollView2.isVisible = isSending
            progressBar.isVisible = isSending.not()
        }
    }

    private fun bindViewModel() {
        // Получаем строку и отправляем ее файлом на сервер
        viewModel.stringJsonSettings.observe(viewLifecycleOwner) {
            Timber.e("СТРОКА - $it")
            viewModel.saveFileToServer(it, selectedDate)
        }
        // Получаем строку json с сервера и парсим её
        viewModel.downloadStringJsonSettings.observe(viewLifecycleOwner) {
            Timber.e("СТРОКА ОТ СЕРВЕРА - $it")
            val moshi = Moshi.Builder()
                .add(CheckingSettingsCustomAdapter())
                .build()

            val adapter =
                moshi.adapter(CheckingSettingsCustomAdapter.CustomCheckingSettings::class.java)
                    .nonNull()

            try {
                val t = adapter.fromJson(it)
                Timber.d("parse t = $t")
                setSettingsIsHistory(t)
            } catch (e: Exception) {
                Timber.d("parse error = ${e.message}")
                ""
            }
        }
        viewModel.isError.observe(viewLifecycleOwner) {
            if (it == "The system cannot find the path specified.") {
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Файл не найден.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Нет подключения к серверу. Проверьте подключение Wi-Fi.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            Timber.d("error = $it")
        }
        viewModel.isSending.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Данные отправлены", Toast.LENGTH_SHORT).show()
        }
        viewModel.isDownloadSettings.observe(viewLifecycleOwner) {
            binding.titleCheckingSettings.isVisible = true
            binding.buttonClear.isVisible = true
            binding.constraint.setBackgroundColor(Color.LTGRAY)
        }
    }

    // Собираем данные с вью во фрагментах
    private suspend fun sendSettings() {
        packageFragment?.onClickSendServer()
        delay(500)

        programTestFragment?.onClickSendServer()
        delay(500)

        assemblyAndLabelFragment?.onClickSendServer()
        delay(500)

        assemblyFragment?.onClickSendServer()
        delay(500)

        speakerTestFragment?.onClickSendServer()
        delay(500)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate() {
        val currentDateTime = LocalDateTime.now()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                // Очищаем все view
                clearWorkspace()
                // Получаем настройки с сервера
                viewModel.downloadFileToServer(
                    year.toString(),
                    "${month + 1}",
                    dayOfMonth.toString()
                )
                binding.titleCheckingSettings.text = "Настройки от $dayOfMonth.${month + 1}.$year"
                selectedDate = "$year/${month + 1}/$dayOfMonth"
            },
            currentDateTime.year,
            currentDateTime.month.value - 1,
            currentDateTime.dayOfMonth
        )
            .show()
    }

    // Устанавливаем настройки полученные с сервера
    private fun setSettingsIsHistory(customCheckingSettings: CheckingSettingsCustomAdapter.CustomCheckingSettings?) {
        customCheckingSettings?.let { checkingSettings ->
            checkingSettings.packaging?.let {
                packageFragment?.setSettingsPackage(it)
            }
            checkingSettings.programTest?.let {
                programTestFragment?.setSettingsProgram(it)
            }
            checkingSettings.assemblyAndLabel?.let {
                assemblyAndLabelFragment?.setSettingsAssemblyAndLabel(it)
            }
            checkingSettings.assembly?.let {
                assemblyFragment?.setSettingsAssembly(it)
            }
            checkingSettings.speakerTest?.let {
                speakerTestFragment?.setSettingsSpeakerTest(it)
            }
        }
    }

    // Очищаем вью
    private fun clearWorkspace() {
        packageFragment?.clearView()
        programTestFragment?.clearView()
        assemblyAndLabelFragment?.clearView()
        assemblyFragment?.clearView()
        speakerTestFragment?.clearView()
    }
}