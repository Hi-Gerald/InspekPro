package com.inspekpro.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.inspekpro.databinding.FragmentScheduleBinding
import com.inspekpro.ui.viewmodel.AuthViewModel
import com.inspekpro.ui.viewmodel.CreateSessionResult
import com.inspekpro.ui.viewmodel.CreateSessionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Bagian Billy: Fragment Jadwal Inspeksi Baru
 * Fitur: Form input jadwal (Nama Mesin, Lokasi, Tanggal, Waktu, Catatan)
 * Tujuan: Memungkinkan user membuat jadwal inspeksi lapangan yang tersimpan sebagai DRAFT.
 */
@AndroidEntryPoint
class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateSessionViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        observeViewModel()
        setupFormDefaults()
    }

    private fun setupFormDefaults() {
        // Set default date/time to now in UI
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        
        binding.etDate.setText(dateFormat.format(calendar.time))
        binding.etTime.setText(timeFormat.format(calendar.time))
        viewModel.scheduledDate.value = calendar.timeInMillis

        // Update inspector name from auth
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.activeUser.collectLatest { user ->
                    user?.let {
                        viewModel.inspectorName.value = it.fullName
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    binding.etDate.setText(dateFormat.format(calendar.time))
                    viewModel.scheduledDate.value = calendar.timeInMillis
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.etTime.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    binding.etTime.setText(timeFormat.format(calendar.time))
                    viewModel.scheduledDate.value = calendar.timeInMillis
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.btnSave.setOnClickListener {
            if (validateInput()) {
                val currentUserId = authViewModel.activeUser.value?.userId?.toString() ?: "0"
                
                viewModel.createSession(
                    inspectorId = currentUserId,
                    manualTitle = binding.etTitle.text.toString().trim(),
                    manualLocation = binding.etLocation.text.toString().trim(),
                    manualConclusion = binding.etNotes.text.toString().trim()
                )
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val title = binding.etTitle.text.toString().trim()
        if (title.isEmpty()) {
            binding.titleInputLayout.error = "Nama mesin harus diisi"
            isValid = false
        } else {
            binding.titleInputLayout.error = null
        }

        val location = binding.etLocation.text.toString().trim()
        if (location.isEmpty()) {
            binding.locationInputLayout.error = "Lokasi harus diisi"
            isValid = false
        } else {
            binding.locationInputLayout.error = null
        }

        if (binding.etDate.text.toString().isEmpty()) {
            binding.dateInputLayout.error = "Tanggal tidak boleh kosong"
            isValid = false
        } else {
            binding.dateInputLayout.error = null
        }

        if (binding.etTime.text.toString().isEmpty()) {
            binding.timeInputLayout.error = "Waktu tidak boleh kosong"
            isValid = false
        } else {
            binding.timeInputLayout.error = null
        }

        return isValid
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createResult.collectLatest { result ->
                    when (result) {
                        is CreateSessionResult.Idle -> {}
                        is CreateSessionResult.Loading -> {
                            binding.btnSave.isEnabled = false
                            binding.btnSave.text = "Menyimpan..."
                        }
                        is CreateSessionResult.Success -> {
                            Toast.makeText(requireContext(), "Jadwal inspeksi berhasil disimpan", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        is CreateSessionResult.Error -> {
                            binding.btnSave.isEnabled = true
                            binding.btnSave.text = "Simpan Jadwal"
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
