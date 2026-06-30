package com.inspekpro.ui

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inspekpro.R
import com.inspekpro.data.local.entity.SessionStatus
import com.inspekpro.databinding.FragmentAddInspectionBinding
import com.inspekpro.ui.viewmodel.CreateSessionResult
import com.inspekpro.ui.viewmodel.CreateSessionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Bagian Billy: UI Tambah Jadwal Inspeksi
 * Fitur: Form input jadwal, validasi input, lampiran foto & video, serta progres checklist.
 * Update: Real-time Camera capture (Photo & Video), Dynamic progress calculation, Editable checklist, Map integration.
 */
@AndroidEntryPoint
class AddInspectionFragment : Fragment() {

    private var _binding: FragmentAddInspectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateSessionViewModel by viewModels()

    private lateinit var checklistAdapter: ChecklistItemAdapter
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var findingPhotoAdapter: PhotoAdapter

    private val checklistItems = mutableListOf(
        Pair("Kondisi Bearing", true),
        Pair("Temperatur Bearing", true),
        Pair("Sistem Grounding", true)
    )

    private val photos = mutableListOf<String>()
    private val findingPhotos = mutableListOf<String>()
    private var videoPath: String? = null
    private val calendar = Calendar.getInstance()

    // Temp URIs for Camera Capture
    private var tempPhotoUri: Uri? = null
    private var isCapturingFinding = false
    private var isCapturingVideo = false

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            if (isCapturingVideo) startCameraForVideo() else startCameraForPhoto()
        } else {
            Toast.makeText(requireContext(), "Izin kamera diperlukan untuk mengambil gambar/video", Toast.LENGTH_SHORT).show()
        }
    }

    // Activity Results for Media
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { addPhotoToList(it.toString(), isFinding = false) }
    }
    
    private val pickFindingImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { addPhotoToList(it.toString(), isFinding = true) }
    }

    private val takePhoto = registerForActivityResult(object : ActivityResultContracts.TakePicture() {
        override fun createIntent(context: Context, input: Uri): Intent {
            return super.createIntent(context, input).apply {
                clipData = ClipData.newRawUri("", input)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
        }
    }) { success ->
        if (success) {
            tempPhotoUri?.let { addPhotoToList(it.toString(), isFinding = isCapturingFinding) }
        }
    }

    private val pickVideo = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            videoPath = it.toString()
            addPhotoToList(it.toString(), isFinding = false)
            binding.tvVideoPath.text = "Video dilampirkan"
            binding.tvVideoPath.setTextColor(resources.getColor(R.color.primary, null))
            updateProgress()
        }
    }

    private val captureVideo = registerForActivityResult(object : ActivityResultContracts.CaptureVideo() {
        override fun createIntent(context: Context, input: Uri): Intent {
            return super.createIntent(context, input).apply {
                clipData = ClipData.newRawUri("", input)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
        }
    }) { success ->
        if (success) {
            tempPhotoUri?.let { 
                videoPath = it.toString()
                addPhotoToList(it.toString(), isFinding = false)
                binding.tvVideoPath.text = "Video direkam"
                binding.tvVideoPath.setTextColor(resources.getColor(R.color.primary, null))
                updateProgress()
            }
        }
    }

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
        setupFormWatchers()
        observeViewModel()
        updateProgress()
    }

    private fun setupFormDefaults() {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        binding.etDate.setText(dateFormat.format(calendar.time))
        binding.etTime.setText(timeFormat.format(calendar.time))
        binding.etInspector.setText("Sofia")
        
        binding.rbNoFindings.isChecked = true
        binding.findingDetailsContainer.visibility = View.GONE
    }

    private fun setupRecyclerViews() {
        checklistAdapter = ChecklistItemAdapter { position, text, isChecked ->
            checklistItems[position] = Pair(text, isChecked)
            updateProgress()
        }
        binding.rvChecklist.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = checklistAdapter
        }
        checklistAdapter.submitList(checklistItems.toList())

        photoAdapter = PhotoAdapter { position ->
            photos.removeAt(position)
            photoAdapter.submitList(photos.toList())
            updateProgress()
        }
        binding.rvPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = photoAdapter
        }
        
        findingPhotoAdapter = PhotoAdapter { position ->
            findingPhotos.removeAt(position)
            findingPhotoAdapter.submitList(findingPhotos.toList())
            updateProgress()
        }
        binding.rvFindingPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = findingPhotoAdapter
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.locationInputLayout.setEndIconOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=Pabrik+Industri")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(mapIntent)
            } else {
                Toast.makeText(requireContext(), "Aplikasi Map tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

        binding.etDate.setOnClickListener {
            DatePickerDialog(requireContext(), { _, y, m, d ->
                calendar.set(y, m, d)
                binding.etDate.setText(SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar.time))
                viewModel.scheduledDate.value = calendar.timeInMillis
                updateProgress()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.etTime.setOnClickListener {
            TimePickerDialog(requireContext(), { _, h, m ->
                calendar.set(Calendar.HOUR_OF_DAY, h)
                calendar.set(Calendar.MINUTE, m)
                binding.etTime.setText(SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time))
                updateProgress()
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        binding.btnChecklistAdd.setOnClickListener {
            checklistItems.add(Pair("", false))
            checklistAdapter.submitList(checklistItems.toList())
            updateProgress()
        }

        binding.btnPhotoAdd.setOnClickListener { showMediaOptions(isFinding = false) }
        binding.btnFindingPhotoAdd.setOnClickListener { showMediaOptions(isFinding = true) }
        binding.btnVideoAdd.setOnClickListener { showVideoOptions() }

        binding.rgFindings.setOnCheckedChangeListener { _, checkedId ->
            binding.findingDetailsContainer.visibility = if (checkedId == R.id.rbHasFindings) View.VISIBLE else View.GONE
            viewModel.hasFindings.value = (checkedId == R.id.rbHasFindings)
            updateProgress()
        }

        binding.btnSaveDraft.setOnClickListener { saveInspection(SessionStatus.DRAFT) }
        binding.btnFinishInspection.setOnClickListener { saveInspection(SessionStatus.COMPLETED) }
    }

    private fun setupFormWatchers() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { updateProgress() }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etTitle.addTextChangedListener(watcher)
        binding.etLocation.addTextChangedListener(watcher)
        binding.etInspector.addTextChangedListener(watcher)
        binding.etConclusion.addTextChangedListener(watcher)
        binding.etFindingCategory.addTextChangedListener(watcher)
        binding.etPriority.addTextChangedListener(watcher)
        binding.etFindingDescription.addTextChangedListener(watcher)
    }

    private fun showMediaOptions(isFinding: Boolean) {
        val options = arrayOf("Ambil Foto", "Pilih dari Galeri")
        AlertDialog.Builder(requireContext())
            .setTitle("Pilih Foto")
            .setItems(options) { _, which ->
                if (which == 0) {
                    isCapturingFinding = isFinding
                    isCapturingVideo = false
                    checkCameraPermissionAndLaunch()
                } else {
                    if (isFinding) pickFindingImage.launch("image/*") else pickImage.launch("image/*")
                }
            }.show()
    }

    private fun checkCameraPermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (isCapturingVideo) startCameraForVideo() else startCameraForPhoto()
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCameraForPhoto() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "JPEG_${timeStamp}_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(fileName, ".jpg", storageDir)
        
        tempPhotoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            imageFile
        )
        takePhoto.launch(tempPhotoUri)
    }

    private fun showVideoOptions() {
        val options = arrayOf("Rekam Video", "Pilih dari Galeri")
        AlertDialog.Builder(requireContext())
            .setTitle("Pilih Video")
            .setItems(options) { _, which ->
                if (which == 0) {
                    isCapturingVideo = true
                    checkCameraPermissionAndLaunch()
                } else {
                    pickVideo.launch("video/*")
                }
            }.show()
    }

    private fun startCameraForVideo() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "VIDEO_${timeStamp}_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        val videoFile = File.createTempFile(fileName, ".mp4", storageDir)
        
        tempPhotoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            videoFile
        )
        captureVideo.launch(tempPhotoUri)
    }

    private fun addPhotoToList(path: String, isFinding: Boolean) {
        if (isFinding) {
            findingPhotos.add(path)
            findingPhotoAdapter.submitList(findingPhotos.toList())
        } else {
            photos.add(path)
            photoAdapter.submitList(photos.toList())
        }
        updateProgress()
    }

    private fun updateProgress() {
        var totalFields = 6 // Title, Location, Date, Time, Inspector, Conclusion
        var filledFields = 0
        
        if (binding.etTitle.text?.isNotBlank() == true) filledFields++
        if (binding.etLocation.text?.isNotBlank() == true) filledFields++
        if (binding.etDate.text?.isNotBlank() == true) filledFields++
        if (binding.etTime.text?.isNotBlank() == true) filledFields++
        if (binding.etInspector.text?.isNotBlank() == true) filledFields++
        if (binding.etConclusion.text?.isNotBlank() == true) filledFields++

        if (checklistItems.isNotEmpty()) {
            totalFields++
            if (checklistItems.all { it.first.isNotBlank() && it.second }) filledFields++
        }

        totalFields += 2
        if (photos.isNotEmpty()) filledFields++
        if (videoPath != null) filledFields++

        if (binding.rbHasFindings.isChecked) {
            totalFields += 4 // Category, Priority, Desc, Finding Photos
            if (binding.etFindingCategory.text?.isNotBlank() == true) filledFields++
            if (binding.etPriority.text?.isNotBlank() == true) filledFields++
            if (binding.etFindingDescription.text?.isNotBlank() == true) filledFields++
            if (findingPhotos.isNotEmpty()) filledFields++
        }

        val percent = ((filledFields.toDouble() / totalFields) * 100).toInt().coerceAtMost(100)
        binding.tvBadgeProgressPercent.text = "$percent%"
        binding.circularProgress.progress = percent
    }

    private fun saveInspection(status: SessionStatus) {
        if (validateInput()) {
            val titleText = binding.etTitle.text.toString().trim()
            val locationText = binding.etLocation.text.toString().trim()
            val inspectorText = binding.etInspector.text.toString().trim()
            val conclusionText = binding.etConclusion.text.toString().trim()
            
            // Pass all data directly to avoid StateFlow propagation delays
            viewModel.createSession(
                inspectorId = "INS-USER-001", 
                status = status,
                manualTitle = titleText,
                manualLocation = locationText,
                manualInspector = inspectorText,
                manualConclusion = conclusionText,
                manualPhotos = photos.toList(),
                manualVideo = videoPath
            )
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        if (binding.etTitle.text.isNullOrBlank()) { 
            binding.titleInputLayout.error = "Wajib diisi"
            isValid = false 
        } else {
            binding.titleInputLayout.error = null
        }
        
        if (binding.etLocation.text.isNullOrBlank()) { 
            binding.locationInputLayout.error = "Wajib diisi"
            isValid = false 
        } else {
            binding.locationInputLayout.error = null
        }
        
        if (binding.etConclusion.text.isNullOrBlank()) { 
            binding.conclusionInputLayout.error = "Wajib diisi"
            isValid = false 
        } else {
            binding.conclusionInputLayout.error = null
        }

        if (!isValid) {
            Toast.makeText(requireContext(), "Harap lengkapi semua field wajib (*)", Toast.LENGTH_SHORT).show()
        }
        
        return isValid
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createResult.collectLatest { result ->
                    when (result) {
                        is CreateSessionResult.Loading -> {
                            binding.btnSaveDraft.isEnabled = false
                            binding.btnFinishInspection.isEnabled = false
                        }
                        is CreateSessionResult.Success -> {
                            Toast.makeText(requireContext(), "Laporan berhasil disimpan!", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        is CreateSessionResult.Error -> {
                            binding.btnSaveDraft.isEnabled = true
                            binding.btnFinishInspection.isEnabled = true
                            Toast.makeText(requireContext(), "Gagal: ${result.message}", Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
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
