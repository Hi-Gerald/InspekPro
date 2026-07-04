package com.inspekpro.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inspekpro.databinding.BottomsheetFilterReportBinding
import java.text.SimpleDateFormat
import java.util.*

class ReportFilterBottomSheet(
    private val initialLocation: String,
    private val initialStartDate: Long?,
    private val initialEndDate: Long?,
    private val initialFindingStatus: String,
    private val onApply: (location: String, startDate: Long?, endDate: Long?, findingStatus: String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomsheetFilterReportBinding? = null
    private val binding get() = _binding!!

    private var selectedStartDate: Long? = initialStartDate
    private var selectedEndDate: Long? = initialEndDate
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetFilterReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind initial values
        binding.etLocation.setText(initialLocation)
        
        initialStartDate?.let {
            binding.etStartDate.setText(dateFormat.format(Date(it)))
        }
        initialEndDate?.let {
            binding.etEndDate.setText(dateFormat.format(Date(it)))
        }

        when (initialFindingStatus) {
            "Has Findings" -> binding.rbHasFindings.isChecked = true
            "No Findings" -> binding.rbNoFindings.isChecked = true
            else -> binding.rbAll.isChecked = true
        }

        // Setup Date Pickers
        binding.etStartDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                selectedStartDate = calendar.timeInMillis
                binding.etStartDate.setText(dateFormat.format(calendar.time))
            }
            val pickerCalendar = Calendar.getInstance()
            selectedStartDate?.let { pickerCalendar.timeInMillis = it }
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                pickerCalendar.get(Calendar.YEAR),
                pickerCalendar.get(Calendar.MONTH),
                pickerCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.etEndDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                selectedEndDate = calendar.timeInMillis
                binding.etEndDate.setText(dateFormat.format(calendar.time))
            }
            val pickerCalendar = Calendar.getInstance()
            selectedEndDate?.let { pickerCalendar.timeInMillis = it }
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                pickerCalendar.get(Calendar.YEAR),
                pickerCalendar.get(Calendar.MONTH),
                pickerCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Reset Button Click
        binding.btnReset.setOnClickListener {
            binding.etLocation.setText("")
            binding.etStartDate.setText("")
            binding.etEndDate.setText("")
            binding.rbAll.isChecked = true
            selectedStartDate = null
            selectedEndDate = null
        }

        // Apply Button Click
        binding.btnApply.setOnClickListener {
            val loc = binding.etLocation.text.toString().trim()
            val finding = when {
                binding.rbHasFindings.isChecked -> "Has Findings"
                binding.rbNoFindings.isChecked -> "No Findings"
                else -> "All"
            }
            onApply(loc, selectedStartDate, selectedEndDate, finding)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
