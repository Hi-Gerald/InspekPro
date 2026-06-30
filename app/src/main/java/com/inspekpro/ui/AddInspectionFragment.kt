package com.inspekpro.ui

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inspekpro.R
import com.inspekpro.databinding.FragmentAddInspectionBinding
import com.inspekpro.ui.viewmodel.AuthViewModel
import com.inspekpro.ui.viewmodel.CreateSessionResult
import com.inspekpro.ui.viewmodel.CreateSessionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Bagian Billy: UI Tambah Jadwal Inspeksi
 * Fitur: Form input jadwal, validasi input, lampiran foto & video, serta progres checklist.
 * Tujuan: Memungkinkan user membuat jadwal inspeksi baru yang nantinya disinkronkan ke Cloud dan Alarm.
 */
@AndroidEntryPoint
class AddInspectionFragment : Fragment() {

    private var _binding: FragmentAddInspectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateSessionViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var checklistAdapter: ChecklistItemAdapter
    private lateinit var photoAdapter: PhotoAdapter

    // Bagian Billy: Launcher untuk Video & Permission
    private val videoPickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            videoPath = it.toString()
            binding.tvVideoPath.text = "Video: Berhasil dilampirkan"
            binding.tvVideoPath.setTextColor(resources.getColor(R.color.primary, null))
            Toast.makeText(requireContext(), "Video laporan berhasil dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    private val photoPickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            photos.add(it.toString())
            photoAdapter.submitList(photos.toList())
            Toast.makeText(requireContext(), "Foto dokumentasi berhasil dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(requireContext(), "Izin notifikasi ditolak. Pengingat mungkin tidak muncul.", Toast.LENGTH_LONG).show()
        }
    }

    private val checklistItems = mutableListOf(
        Pair("Tekan Pompa *", true),
        Pair("Kondisi Bearing *", true),
        Pair("Kebocoran *", true)
    )

    private val photos = mutableListOf<String>()
    private var videoPath: String? = null
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddInspectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNotificationPermission()
        setupFormDefaults()
        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()
        updateProgress()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setupFormDefaults() {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        binding.etDate.setText(dateFormat.format(calendar.time))
        binding.etTime.setText(timeFormat.format(calendar.time))
        
        // Bagian Billy: Ambil data user login secara dinamis
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.activeUser.collectLatest { user ->
                    user?.let {
                        binding.etInspector.setText(it.fullName)
                    }
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        checklistAdapter = ChecklistItemAdapter { position, isChecked ->
            checklistItems[position] = checklistItems[position].copy(second = isChecked)
            updateProgress()
        }
        binding.rvChecklist.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = checklistAdapter
            isNestedScrollingEnabled = false
        }
        checklistAdapter.submitList(checklistItems.toList())

        photoAdapter = PhotoAdapter { position ->
            photos.removeAt(position)
            photoAdapter.submitList(photos.toList())
        }
        binding.rvPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = photoAdapter
            isNestedScrollingEnabled = false
        }
        photoAdapter.submitList(photos.toList())
    }

    private fun setupClickListeners() {
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
                    // Pastikan waktu alarm terupdate di ViewModel
                    viewModel.scheduledDate.value = calendar.timeInMillis
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.btnChecklistAdd.setOnClickListener {
            val newItemTitle = "Pemeriksaan Baru ${checklistItems.size + 1} *"
            checklistItems.add(Pair(newItemTitle, false))
            checklistAdapter.submitList(checklistItems.toList())
            updateProgress()
            Toast.makeText(requireContext(), "Item pemeriksaan ditambahkan", Toast.LENGTH_SHORT).show()
        }

        binding.btnPhotoAdd.setOnClickListener {
            // Bagian Billy: Menggunakan Photo Picker asli (Bukan hardcoded picsum)
            photoPickerLauncher.launch("image/*")
        }

        binding.btnVideoAdd.setOnClickListener {
            // Bagian Billy: Menggunakan Video Picker asli (Bukan hardcoded)
            videoPickerLauncher.launch("video/*")
        }

        binding.btnSaveInspection.setOnClickListener {
            if (validateInput()) {
                viewModel.title.value = binding.etTitle.text.toString().trim()
                viewModel.locationName.value = binding.etLocation.text.toString().trim()
                viewModel.inspectorName.value = binding.etInspector.text.toString().trim()
                viewModel.videoPath.value = videoPath
                
                // Kirim data progres checklist (Laporan)
                val totalItems = checklistItems.size
                val passedItems = checklistItems.count { it.second }
                
                // Ambil ID user dari AuthViewModel
                val currentUserId = authViewModel.activeUser.value?.userId?.toString() ?: "0"
                viewModel.createSession(currentUserId, totalItems, passedItems)
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val title = binding.etTitle.text.toString().trim()
        if (title.isEmpty()) {
            binding.titleInputLayout.error = "Nama objek tidak boleh kosong"
            isValid = false
        } else {
            binding.titleInputLayout.error = null
        }

        val location = binding.etLocation.text.toString().trim()
        if (location.isEmpty()) {
            binding.locationInputLayout.error = "Lokasi tidak boleh kosong"
            isValid = false
        } else {
            binding.locationInputLayout.error = null
        }

        val inspector = binding.etInspector.text.toString().trim()
        if (inspector.isEmpty()) {
            binding.inspectorInputLayout.error = "Nama inspektor harus diisi"
            isValid = false
        } else {
            binding.inspectorInputLayout.error = null
        }

        if (photos.isEmpty()) {
            Toast.makeText(requireContext(), "Minimal lampirkan 1 foto dokumentasi", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (videoPath == null) {
            Toast.makeText(requireContext(), "Harap lampirkan video laporan", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun updateProgress() {
        val checkedCount = checklistItems.count { it.second }
        val percent = if (checklistItems.isNotEmpty()) {
            ((checkedCount.toDouble() / checklistItems.size) * 100).toInt()
        } else {
            0
        }
        binding.tvBadgeProgressPercent.text = "$percent%"
        binding.circularProgress.progress = percent
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createResult.collectLatest { result ->
                    when (result) {
                        is CreateSessionResult.Idle -> {}
                        is CreateSessionResult.Loading -> {
                            binding.btnSaveInspection.isEnabled = false
                            binding.btnSaveInspection.text = "Menyimpan..."
                        }
                        is CreateSessionResult.Success -> {
                            Toast.makeText(requireContext(), "Laporan inspeksi disimpan & alarm dijadwalkan!", Toast.LENGTH_LONG).show()
                            findNavController().popBackStack()
                        }
                        is CreateSessionResult.Error -> {
                            binding.btnSaveInspection.isEnabled = true
                            binding.btnSaveInspection.text = "Simpan Laporan"
                            Toast.makeText(requireContext(), "Gagal: ${result.message}", Toast.LENGTH_LONG).show()
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
