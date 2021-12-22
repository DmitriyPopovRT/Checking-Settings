package ru.popov.checkingsettings.ui.statictics

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.CheckingSettingsCustomAdapter
import ru.popov.checkingsettings.databinding.FragmentStatisticsBinding
import ru.popov.checkingsettings.ui.home.HomeViewModel
import ru.popov.checkingsettings.utils.Utils.toast
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val binding: FragmentStatisticsBinding by viewBinding(FragmentStatisticsBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var yearFrom = 0
    private var monthFrom = 0
    private var dayFrom = 0

    private var yearTo = 0
    private var monthTo = 0
    private var dayTo = 0

    private var listStatistics =
        mutableMapOf<Date, CheckingSettingsCustomAdapter.ValueAndCalibration>()

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinnerOperation()
        initSpinnerWorkplace(R.array.spinner_workplace1)
        checkingForEmptyStringSpinner(
            binding.textInputLayoutOperation,
            binding.spinnerOperation
        )

        bindViewModel()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(StatisticsFragmentDirections.actionStatisticsFragmentToHomeFragment())
        }

        binding.dateFrom.setOnClickListener {
            setData("from")
        }

        binding.dateTo.setOnClickListener {
            setData("to")
        }

        binding.buttonPaint.setOnClickListener {
            if (
                checkingForEmptyStringSpinner(
                    binding.textInputLayoutOperation,
                    binding.spinnerOperation
                ) &&
                checkingForEmptyStringSpinner(
                    binding.textInputLayoutWorkPlace,
                    binding.spinnerWorkPlace
                ) &&
                checkingForEmptyStringEditText(binding.textDateFrom, binding.dateFrom) &&
                checkingForEmptyStringEditText(binding.textDateTo, binding.dateTo)
            ) {
                paintGraph()
            }
        }
    }

    private fun bindViewModel() {
        // Получаем строку json с сервера и парсим её
        viewModel.downloadStringJsonSettings.observe(viewLifecycleOwner) {
            try {
                Timber.d("adapter = ${it?.assembly}")

            } catch (e: Exception) {
                Timber.d("error = ${e.message}")
            }
        }
        viewModel.isError.observe(viewLifecycleOwner) {
            Timber.d("error = $it")
        }

        viewModel.listStatistics.observe(viewLifecycleOwner) {
            Timber.d("list = $it")
            listStatistics.putAll(it)
        }
    }

    private fun paintGraph() {
        binding.progressBar.visibility = View.VISIBLE
        binding.linearLayout.visibility = View.GONE
        val operation = binding.spinnerOperation.text.toString()
        val workPlace = binding.spinnerWorkPlace.text.toString()

        val df = SimpleDateFormat("yyyy-MM-dd")
        val calendarFrom: Calendar = GregorianCalendar(yearFrom, monthFrom, dayFrom)
        val calendarTo: Calendar = GregorianCalendar(yearTo, monthTo, dayTo)
        calendarTo.add(Calendar.DAY_OF_MONTH, 1)

        CoroutineScope(Dispatchers.Default).launch {
            while (calendarFrom.before(calendarTo)) {
                Timber.d("день = ${df.format(calendarFrom.time)}")

                val year = calendarFrom.get(Calendar.YEAR)
                val month = (calendarFrom.get(Calendar.MONTH) + 1)
                val day = calendarFrom.get(Calendar.DAY_OF_MONTH)

//                viewModel.downloadFileToServer(year, month, day)
                viewModel.downloadStatistics(year, month, day, operation, workPlace)
                Thread.sleep(200)
                calendarFrom.add(Calendar.DAY_OF_MONTH, 1)
            }

            withContext(Dispatchers.Main) {
                Timber.d("initGraph list = $listStatistics")
                initGraph(listStatistics, operation)

                binding.progressBar.visibility = View.GONE
                binding.linearLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun initSpinnerOperation() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_operation,
            R.layout.dropdown_menu_popup_item
        )
        binding.spinnerOperation.setAdapter(adapter)
    }

    private fun initSpinnerWorkplace(textArray: Int) {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            textArray,
            R.layout.dropdown_menu_popup_item
        )
        binding.spinnerWorkPlace.setAdapter(adapter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData(str: String) {
        val currentDateTime = LocalDateTime.now()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val localDate = LocalDate.of(year, month + 1, dayOfMonth)
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                if (str == "from") {
                    binding.dateFrom.setText(localDate.format(formatter))

                    yearFrom = year
                    monthFrom = month
                    dayFrom = dayOfMonth
                } else {
                    binding.dateTo.setText(localDate.format(formatter))

                    yearTo = year
                    monthTo = month
                    dayTo = dayOfMonth
                }
            },
            if (str == "from") if (yearFrom == 0) currentDateTime.year else yearFrom
            else if (yearTo == 0) currentDateTime.year else yearTo,
            if (str == "from") if (monthFrom == 0) currentDateTime.month.value - 1 else monthFrom
            else if (monthTo == 0) currentDateTime.month.value - 1 else monthTo,
            if (str == "from") if (dayFrom == 0) currentDateTime.dayOfMonth else dayFrom
            else if (dayTo == 0) currentDateTime.dayOfMonth else dayTo,
//            currentDateTime.year,
//            currentDateTime.month.value - 1,
//            currentDateTime.dayOfMonth
        )
            .show()
    }

    // Метод для построения графика
    private fun initGraph(list: MutableMap<Date, CheckingSettingsCustomAdapter.ValueAndCalibration>, operation: String) {
        // Удаляем предыдущие графики
        binding.linearLayout.removeAllViews()

        // Объявляем график(выделяем память)
        val graph = GraphView(requireContext())
        val dateFormatterHour = SimpleDateFormat("dd.MM.yyyy")
//        val dataPoint = arrayOfNulls<DataPoint>(list.size)
        val dataPoint = arrayListOf<DataPoint>()
        val dataPointIsCalibration = arrayListOf<DataPoint>()

//        var j = 0
        // Пробегаем по заполненному массиву точек для графика
        for (map in list) {
            val date = map.key

            if (map.value.isCalibration == true) {
                dataPointIsCalibration.add(DataPoint(date, 0.0))
                dataPointIsCalibration.add(DataPoint(date, 1000.0))
            }

            if (map.value.value?.isEmpty() == true)
                continue
            val value = map.value.value?.toDouble() ?: 0.0
            dataPoint.add(DataPoint(date, value))
//            dataPoint[j] = value?.let { DataPoint(date, it) }
//            j++
        }

        var j = 0
        val arrayDataPoint = arrayOfNulls<DataPoint>(dataPoint.size)
        for (data in dataPoint) {
            arrayDataPoint[j] = data
            j++
        }

        var k = 0
        val arrayIsCalibration = arrayOfNulls<DataPoint>(dataPointIsCalibration.size)
        for (data in dataPointIsCalibration) {
            arrayIsCalibration[k] = data
            k++
        }

        // Создаем серию
        val seriesLine = LineGraphSeries(arrayDataPoint)
        seriesLine.setOnDataPointTapListener { _, dataPoint ->
            toast("Дата ${dateFormatterHour.format(dataPoint.x)}, значение = ${dataPoint.y}")
        }
        // Делаем линию графика с выраженными точками
        seriesLine.isDrawDataPoints = true
        seriesLine.color = Color.BLUE
        graph.gridLabelRenderer.labelFormatter =
            DateAsXAxisLabelFormatter(requireContext(), dateFormatterHour)
        graph.gridLabelRenderer.numHorizontalLabels = 10
        graph.gridLabelRenderer.numVerticalLabels = 11

        val d1 = GregorianCalendar(yearFrom, monthFrom, dayFrom).time
        val d2 = GregorianCalendar(yearTo, monthTo, dayTo).time

        graph.viewport.setMinX(d1.time.toDouble())
        graph.viewport.setMaxX(d2.time.toDouble())
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.isYAxisBoundsManual = true
//        graph.getViewport().setScrollable(true); // enables horizontal scrolling
//        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.viewport.isScalable = true // enables horizontal zooming and scrolling
        //        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        graph.gridLabelRenderer.isHumanRounding = false


        val dataPointLimitTop = arrayOfNulls<DataPoint>(2)
        val dataPointLimitBottom = arrayOfNulls<DataPoint>(2)
        when (operation) {
            getString(R.string.assembly_eb) -> {
                dataPointLimitTop[0] = DataPoint(d1, 3.5)
                dataPointLimitTop[1] = DataPoint(d2, 3.5)
                dataPointLimitBottom[0] = DataPoint(d1, 2.5)
                dataPointLimitBottom[1] = DataPoint(d2, 2.5)
                graph.viewport.setMinY(2.0)
                graph.viewport.setMaxY(4.0)
            }
            getString(R.string.assembly_bip) -> {
                dataPointLimitTop[0] = DataPoint(d1, 1.2)
                dataPointLimitTop[1] = DataPoint(d2, 1.2)
                dataPointLimitBottom[0] = DataPoint(d1, 0.8)
                dataPointLimitBottom[1] = DataPoint(d2, 0.8)
                graph.viewport.setMinY(0.5)
                graph.viewport.setMaxY(1.5)
            }
            getString(R.string.assembly_speaker) -> {
                dataPointLimitTop[0] = DataPoint(d1, 2.5)
                dataPointLimitTop[1] = DataPoint(d2, 2.5)
                dataPointLimitBottom[0] = DataPoint(d1, 1.9)
                dataPointLimitBottom[1] = DataPoint(d2, 1.9)
                graph.viewport.setMinY(1.5)
                graph.viewport.setMaxY(3.0)
            }
            getString(R.string.assembly_temp) -> {
                dataPointLimitTop[0] = DataPoint(d1, 350.0)
                dataPointLimitTop[1] = DataPoint(d2, 350.0)
                dataPointLimitBottom[0] = DataPoint(d1, 400.0)
                dataPointLimitBottom[1] = DataPoint(d2, 400.0)
                graph.viewport.setMinY(300.0)
                graph.viewport.setMaxY(450.0)
            }
            getString(R.string.assembly_fixation) -> {
                dataPointLimitTop[0] = DataPoint(d1, 3.5)
                dataPointLimitTop[1] = DataPoint(d2, 3.5)
                dataPointLimitBottom[0] = DataPoint(d1, 2.5)
                dataPointLimitBottom[1] = DataPoint(d2, 2.5)
                graph.viewport.setMinY(2.0)
                graph.viewport.setMaxY(4.0)
            }
        }
        // Создаем серию
        val seriesLimitTop = LineGraphSeries(dataPointLimitTop)
        val seriesLimitBottom = LineGraphSeries(dataPointLimitBottom)
        val seriesIsCalibration = LineGraphSeries(arrayIsCalibration)
        seriesLimitTop.color = Color.RED
        seriesLimitBottom.color = Color.RED
        seriesIsCalibration.color = Color.CYAN
        graph.addSeries(seriesLimitTop)
        graph.addSeries(seriesLimitBottom)
        graph.addSeries(seriesLine)
        graph.addSeries(seriesIsCalibration)

        binding.linearLayout.addView(graph)

        listStatistics.clear()
    }

    private fun checkingForEmptyStringEditText(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText
    ): Boolean {
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (textInputEditText.text.toString().isNotEmpty()) {
                    textInputLayout.error = null
                    textInputLayout.isErrorEnabled = false
                } else {
                    textInputLayout.error = getString(R.string.error_required_input_field)
                }
            }
        })
        return if (textInputEditText.text.toString().isNotEmpty()) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error = getString(R.string.error_required_input_field)
            false
        }
    }

    private fun checkingForEmptyStringSpinner(
        textInputLayout: TextInputLayout,
        spinner: AutoCompleteTextView
    ): Boolean {
        spinner.setOnItemClickListener { _, view, _, _ ->
            textInputLayout.isErrorEnabled = false

            if (spinner == binding.spinnerOperation) {
                binding.spinnerWorkPlace.text.clear()
                val textInput = view as MaterialTextView
                if (textInput.text == getString(R.string.assembly_fixation)) {
                    Timber.d("bench")

                    initSpinnerWorkplace(R.array.spinner_workplace2)
                } else {
                    initSpinnerWorkplace(R.array.spinner_workplace1)
                }
            }
        }

        return if (spinner.text.toString().isNotEmpty()) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error = getString(R.string.error_required_input_field)
            false
        }
    }
}