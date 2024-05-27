package ru.popov.checkingsettings.ui.home

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentHomeBinding
import ru.popov.checkingsettings.ui.AssemblyAndLabelFragment
import ru.popov.checkingsettings.ui.AssemblyFragment
import ru.popov.checkingsettings.ui.ExitDeviceFragment
import ru.popov.checkingsettings.ui.OutageFragment
import ru.popov.checkingsettings.ui.PackageFragment
import ru.popov.checkingsettings.ui.ProgramTestFragment
import ru.popov.checkingsettings.ui.SpeakerTestFragment
import ru.popov.checkingsettings.ui.TestBipFragment
import ru.popov.checkingsettings.ui.TestConnectorFragment
import ru.popov.checkingsettings.utils.LoginInformation
import ru.popov.checkingsettings.utils.SettingsParse
import ru.popov.checkingsettings.utils.Utils.toast
import timber.log.Timber
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var packageFragment: PackageFragment? = null
    private var programTestFragment: ProgramTestFragment? = null
    private var assemblyAndLabelFragment: AssemblyAndLabelFragment? = null
    private var assemblyFragment: AssemblyFragment? = null
    private var speakerTestFragment: SpeakerTestFragment? = null
    private var bipTestFragment: TestBipFragment? = null
    private var testConnectorFragment: TestConnectorFragment? = null
    private var outageFragment: OutageFragment? = null
    private var exitDeviceFragment: ExitDeviceFragment? = null

    private var selectedDateAsPath = ""

    private val currentDateTime = LocalDateTime.now()
    private var selectedYear = currentDateTime.year
    private var selectedMonth = currentDateTime.month.value
    private var selectedDay = currentDateTime.dayOfMonth

    lateinit var settingsParse: SettingsParse

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("TAG", "onViewCreated")
        // Парсим настройки подключения
        settingsParse = SettingsParse(requireContext())
        LoginInformation.PATH = settingsParse.path
        LoginInformation.SERVERNAME = settingsParse.serverName
        LoginInformation.SHARENAME = settingsParse.shareName
        LoginInformation.USER = settingsParse.login
        LoginInformation.PASS = settingsParse.password

        Timber.d("onViewCreated")
        // Получаем инстансы фрагментов
        getFragment()

        // Подписываемся на обновления вьюмодели
        bindViewModel()

        binding.buttonSendServer.setOnClickListener {
            // Проверяем существует ли файл настроек. Если существует, отправляем данные
            viewModel.isExistFile(
                selectedYear.toString(),
                selectedMonth.toString(),
                selectedDay.toString()
            )
        }

        // Кнопка загрузки настроек
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_load_settings -> {
                    // Получаем дату которую выбрал пользователь
                    getDate()
                    true
                }
                R.id.action_settings -> {
                    // Вызываем метод отрисовки настроек
                    getShowDialogSettings()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }

        // Кнопка очистить(Х)
        // При нажатии закрывается история и показываются настройки сегодняшнего дня
        binding.buttonClear.setOnClickListener {
            setTodayDate()
            loadSettings()
            selectedDateAsPath = ""
        }

        // Кнопка прикрепить фото
        binding.attachButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToImagesFragment(selectedDateAsPath)
            )
        }

        // Кнопка статистика
        binding.statisticsButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToStatisticsFragment()
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("TAG", "onSaveInstanceState")
//        outState.putSerializable("list", myData as Serializable?)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("TAG", "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("TAG", "onActivityCreated")
//        if (savedInstanceState != null) {
//            //probably orientation change
//            myData = savedInstanceState.getSerializable("list") as List<String?>?
//        } else {
//            if (myData != null) {
//                //returning from backstack, data is fine, do nothing
//            } else {
//                //newly created, compute data
//                myData = computeData()
//            }
//        }
    }

    // Создаем окно с настройками
    private fun getShowDialogSettings() {
        val alertDialog = requireActivity().let {
            val myBuilder = androidx.appcompat.app.AlertDialog.Builder(it)
            // Парсим настройки при открытии диалога
            settingsParse = SettingsParse(requireContext())
            // Инфлэйтим вьюху
            val view = requireActivity()
                .layoutInflater
                .inflate(R.layout.dialog_layout_setting, null)

            val server = view.findViewById(R.id.editServerName) as EditText
            val share = view.findViewById(R.id.editShareName) as EditText
            val path = view.findViewById(R.id.editPathToFolder) as EditText
            val user = view.findViewById(R.id.editDialogSettingUser) as EditText
            val password = view.findViewById(R.id.editDialogSettingPassword) as EditText

            // Заполняем поля из json
            server.setText(settingsParse.serverName)
            share.setText(settingsParse.shareName)
            path.setText(settingsParse.path)
            user.setText(settingsParse.login)
            password.setText(settingsParse.password)

            myBuilder
                .setTitle(requireActivity().resources.getString(R.string.settings))
                .setView(view)
                .setPositiveButton(
                    requireActivity()
                        .resources
                        .getString(R.string.ok)
                ) { _, _ ->
                    // В побочном потоке записываем настройки в файл
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                val currentPath =
                                    activity?.applicationContext?.getFileStreamPath(
                                        "settings.jsonc"
                                    )?.path
                                // открываем поток для записи
                                val bw = BufferedWriter(FileWriter(currentPath))
                                val settings = JSONObject()
                                settings.put("serverName", server.text.toString())
                                settings.put("shareName", share.text.toString())
                                settings.put("path", path.text.toString())
                                settings.put("user", user.text.toString())
                                settings.put("password", password.text.toString())

                                // пишем данные
                                bw.write(settings.toString())
                                // закрываем поток
                                bw.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
                .setNegativeButton(
                    requireActivity()
                        .resources
                        .getString(R.string.cancel)
                ) { _, _ -> }
                .create()
        }
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")

        // Загружаем настройки выбранного дня. По умолчанию сегодняшний
        loadSettings()

        assemblyFragment?.onLongClickEditText()
    }

    // Диалог, возникающий если пользователь пытается перезаписать файл настроек, который уже есть
    // на сервере. Если пользователь вводит пароль администратора, то данные перезаписываются
    private fun adminVerification() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.access_confirmation))

            val etInput = EditText(requireContext()).apply {
                setSingleLine()
                inputType = EditorInfo.TYPE_CLASS_NUMBER
            }

            setView(LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                addView(TextView(requireContext()).apply {
                    text = getString(R.string.admin_access_query)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                })
                addView(etInput)
                setPadding(32, 32, 32, 32)
            })
            setCancelable(false)

            setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, _ -> dialog.cancel() }

            setPositiveButton(
                getString(R.string.ok)
            ) { _, _ ->
                val et = etInput.text.toString()
                if (et != LoginInformation.ADMIN_PASSWORD) {
                    etInput.setText("")
                    toast(R.string.password_incorrect)
                    adminVerification()
                } else {
                    sendSettingsToServer()
                    toast(R.string.access_confirmed)
                }
            }
        }.create().show()
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
        bipTestFragment =
            childFragmentManager.findFragmentByTag("fragmentBipTest") as TestBipFragment?
        testConnectorFragment =
            childFragmentManager.findFragmentByTag("fragmentTestConnector") as TestConnectorFragment?
        outageFragment =
            childFragmentManager.findFragmentByTag("fragmentOutage") as OutageFragment?
        exitDeviceFragment =
            childFragmentManager.findFragmentByTag("fragmentExitDevice") as ExitDeviceFragment?
    }

    // Показываем/скрываем progressBar
    private fun showProgressSend(isSending: Boolean) {
        with(binding) {
            buttonSendServer.isEnabled = isSending.not()
            scrollView2.isVisible = isSending.not()
            progressBar.isVisible = isSending
        }
    }

    private fun bindViewModel() {
        // Получаем строку и отправляем ее файлом на сервер
        viewModel.stringJsonSettings.observe(viewLifecycleOwner) {
            Timber.e("СТРОКА - $it")
            viewModel.saveFileToServer(it, selectedDateAsPath)
        }
        // Получаем строку json с сервера и парсим её
        viewModel.downloadStringJsonSettings.observe(viewLifecycleOwner) {
            try {
                setSettingsIsHistory(it)
            } catch (e: Exception) {
                Timber.d("error1 = ${e.message}")
            }
        }
        viewModel.isError.observe(viewLifecycleOwner) {
            when (it) {
                "The system cannot find the path specified." -> {
                    toast(R.string.path_not_found)
                }
                "The system cannot find the file specified." -> {
                    if (!isTodaySettings())
                        toast(R.string.file_not_found)
                }
                "Failed to connect to server" -> {
                    toast(R.string.no_connection)
                }
                else -> {
                    Toast.makeText(requireContext(), "$it", Toast.LENGTH_LONG).show()
//                    toast(it)
                }
            }

            Timber.d("error2 = $it")
        }
        viewModel.isSending.observe(viewLifecycleOwner) {
            toast(R.string.data_send)
        }
        viewModel.isFileSettingsExist.observe(viewLifecycleOwner) {
            if (!it) {
                sendSettingsToServer()
            } else {
                if (isTodaySettings())
                    sendSettingsToServer()
                else
                    adminVerification()
            }
        }
    }

    // Отправка настроек на сервер
    private fun sendSettingsToServer() {
        lifecycleScope.launch {
            showProgressSend(true)
            // Отправляем команды собрать информацию с вью и записать в строки json
            sendSettings()

            // Генерируем общую строку и отправляем на сервер
            viewModel.generateJson()
            showProgressSend(false)
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
        bipTestFragment?.onClickSendServer()
        delay(500)
        testConnectorFragment?.onClickSendServer()
        delay(500)
        outageFragment?.onClickSendServer()
        delay(500)
        exitDeviceFragment?.onClickSendServer()
        delay(500)
    }

    private fun getDate() {
//        val currentDateTime = LocalDateTime.now()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedYear = year
                selectedMonth = month + 1
                selectedDay = dayOfMonth
                selectedDateAsPath = "$year/${month + 1}/$dayOfMonth"

                loadSettings()
            },
            selectedYear,
            selectedMonth - 1,
            selectedDay
//            if (selectedYear == 0) currentDateTime.year else selectedYear,
//            if (selectedMonth == 0) currentDateTime.month.value - 1 else selectedMonth - 1,
//            if (selectedDay == 0) currentDateTime.dayOfMonth else selectedDay
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
            checkingSettings.bipTest?.let {
                bipTestFragment?.setSettingsBipTest(it)
            }
            checkingSettings.testConnectorWrapper?.let {
                testConnectorFragment?.setSettingsTestConnector(it)
            }
            checkingSettings.outageWrapper?.let {
                outageFragment?.setSettingsOutage(it)
            }
            checkingSettings.exitDeviceWrapper?.let {
                exitDeviceFragment?.setSettingsExitDevice(it)
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
        bipTestFragment?.clearView()
        testConnectorFragment?.clearView()
        outageFragment?.clearView()
        exitDeviceFragment?.clearView()
    }

    private fun loadSettings() {
        // Очищаем все view
        clearWorkspace()

        val currentDateTime = LocalDateTime.now()

        if (isTodaySettings()) {
            scene1()
        } else {
            scene2()
        }

        Timber.d("loadSettings")
        // Получаем настройки с сервера
        viewModel.downloadFileToServer(
            selectedYear.toString(),
            selectedMonth.toString(),
            selectedDay.toString()
        )
    }

    private fun scene1() {
        binding.titleCheckingSettings.isVisible = false
        binding.buttonClear.isVisible = false
        binding.constraint.setBackgroundColor(Color.WHITE)
    }

    private fun scene2() {
        binding.titleCheckingSettings.text =
            "Настройки от $selectedDay.$selectedMonth.$selectedYear"

        binding.titleCheckingSettings.isVisible = true
        binding.buttonClear.isVisible = true
        binding.constraint.setBackgroundColor(Color.LTGRAY)
    }

    private fun setTodayDate() {
        val currentDateTime = LocalDateTime.now()
        selectedYear = currentDateTime.year
        selectedMonth = currentDateTime.month.value
        selectedDay = currentDateTime.dayOfMonth
    }

    private fun isTodaySettings(): Boolean {
        val currentDateTime = LocalDateTime.now()
        return selectedYear == currentDateTime.year &&
                selectedMonth == currentDateTime.month.value &&
                selectedDay == currentDateTime.dayOfMonth
    }
}