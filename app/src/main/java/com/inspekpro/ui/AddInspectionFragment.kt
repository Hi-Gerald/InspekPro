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
import androidx.recyclerview.widget.LinearLayoutManager
import com.inspekpro.R
import com.inspekpro.databinding.FragmentAddInspectionBinding
import com.inspekpro.ui.viewmodel.CreateSessionResult
import com.inspekpro.ui.viewmodel.CreateSessionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddInspectionFragment : Fragment() {

    private var _binding: FragmentAddInspectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateSessionViewModel by viewModels()

    private lateinit var checklistAdapter: ChecklistItemAdapter
    private lateinit var photoAdapter: PhotoAdapter

    private val checklistItems = mutableListOf(
        Pair("Tekan Pompa *", true),
        Pair("Kondisi Bearing *", true),
        Pair("Kebocoran *", true)
    )

    private val photos = mutableListOf<String>()
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

        setupFormDefaults()
        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()
        updateProgress()
    }

    private fun setupFormDefaults() {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        binding.etDate.setText(dateFormat.format(calendar.time))
        binding.etTime.setText(timeFormat.format(calendar.time))
        binding.etInspector.setText("Sofia")
    }

    private fun setupRecyclerViews() {
        // Checklist Recycler
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

        // Photos Recycler
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
        // Date picker trigger
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

        // Time picker trigger
        binding.etTime.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    binding.etTime.setText(timeFormat.format(calendar.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        // Add Checklist Item dynamically
        binding.btnChecklistAdd.setOnClickListener {
            val newItemTitle = "Pemeriksaan Baru ${checklistItems.size + 1} *"
            checklistItems.add(Pair(newItemTitle, false))
            checklistAdapter.submitList(checklistItems.toList())
            updateProgress()
            Toast.makeText(requireContext(), "Item pemeriksaan ditambahkan", Toast.LENGTH_SHORT).show()
        }

        // Add Photo dynamically
        binding.btnPhotoAdd.setOnClickListener {
            val randomImgUrl = "https://picsum.photos/id/${(10..100).random()}/200/200"
            photos.add(randomImgUrl)
            photoAdapter.submitList(photos.toList())
            Toast.makeText(requireContext(), "Foto ditambahkan", Toast.LENGTH_SHORT).show()
        }

        // Save Button trigger
        binding.btnSaveInspection.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val location = binding.etLocation.text.toString().trim()
            val inspector = binding.etInspector.text.toString().trim()

            if (title.isEmpty()) {
                binding.titleInputLayout.error = "Nama objek tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.titleInputLayout.error = null
            }

            if (location.isEmpty()) {
                binding.locationInputLayout.error = "Lokasi tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.locationInputLayout.error = null
            }

            viewModel.title.value = title
            viewModel.locationName.value = location
            viewModel.inspectorName.value = inspector
            viewModel.createSession("INS-001") // Mock inspector ID
        }
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
                            Toast.makeText(requireContext(), "Laporan inspeksi disimpan!", Toast.LENGTH_LONG).show()
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
