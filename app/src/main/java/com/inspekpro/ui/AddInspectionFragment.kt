package com.inspekpro.ui

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.inspekpro.R
import com.inspekpro.data.local.entity.SessionStatus
import com.inspekpro.databinding.FragmentAddInspectionBinding
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
 * Update: Real-time Camera capture (Photo & Video), Dynamic progress calculation, Editable checklist, Map integration.
 */
@AndroidEntryPoint
class AddInspectionFragment : Fragment() {

    private var _binding: FragmentAddInspectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateSessionViewModel by viewModels()
    private val inspectionId by lazy { arguments?.getLong("sessionId") ?: -1L }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var checklistAdapter: ChecklistItemAdapter
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var findingPhotoAdapter: PhotoAdapter

    private val checklistItems = mutableListOf<Pair<String, Boolean>>()

    private val photos = mutableListOf<String>()
    private val findingPhotos = mutableListOf<String>()
    private var videoPath: String? = null
    private val calendar = Calendar.getInstance()

    // Temp URIs for Camera Capture
    private var isCapturingFinding = false
    private var isCapturingVideo = false

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            checkCameraPermissionAndLaunch()
        } else {
            Toast.makeText(requireContext(), "Izin kamera diperlukan untuk mengambil gambar/video", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestLocationPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            getCurrentLocation()
        } else {
            Toast.makeText(requireContext(), "Izin lokasi diperlukan untuk fitur ini", Toast.LENGTH_SHORT).show()
        }
    }

    // Activity Results for Media
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            val size = getUriSizeInBytes(requireContext(), it.toString())
            if (size > 0 && size < 100 * 1024) {
                Toast.makeText(requireContext(), "Ukuran foto minimal 100 KB (.jpg/.png)", Toast.LENGTH_SHORT).show()
            } else {
                addPhotoToList(it.toString(), isFinding = false) 
            }
        }
    }
    
    private val pickFindingImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            val size = getUriSizeInBytes(requireContext(), it.toString())
            if (size > 0 && size < 100 * 1024) {
                Toast.makeText(requireContext(), "Ukuran foto temuan minimal 100 KB (.jpg/.png)", Toast.LENGTH_SHORT).show()
            } else {
                addPhotoToList(it.toString(), isFinding = true) 
            }
        }
    }

    private val pickVideo = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            val size = getUriSizeInBytes(requireContext(), it.toString())
            if (size > 0 && size < 1024 * 1024) {
                Toast.makeText(requireContext(), "Ukuran video minimal 1 MB (.mp4)", Toast.LENGTH_SHORT).show()
            } else {
                videoPath = it.toString()
                addPhotoToList(it.toString(), isFinding = false)
                binding.tvVideoPath.text = "Video dilampirkan"
                binding.tvVideoPath.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        
        if (inspectionId != -1L) {
            binding.tvHeaderTitle.text = "Edit Inspeksi"
            viewModel.loadSession(inspectionId)
        }

        applyWindowInsets()
        setupFormDefaults()
        setupRecyclerViews()
        setupClickListeners()
        setupFormWatchers()
        observeViewModel()
        setupFragmentResultListeners()
        updateProgress()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackNavigation()
            }
        })
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val density = resources.displayMetrics.density

            // Bottom Insets
            binding.footerContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val baseMargin = (16 * density).toInt()
                bottomMargin = baseMargin + systemBars.bottom
            }

            // Top Insets
            binding.btnBack.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val baseMargin = (16 * density).toInt()
                topMargin = baseMargin + systemBars.top
            }
            binding.tvHeaderTitle.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val baseMargin = (24 * density).toInt()
                topMargin = baseMargin + systemBars.top
            }
            binding.progressBadge.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val baseMargin = (24 * density).toInt()
                topMargin = baseMargin + systemBars.top
            }

            insets
        }
        androidx.core.view.ViewCompat.requestApplyInsets(binding.root)
    }

    private fun setupFragmentResultListeners() {
        setFragmentResultListener("camera_result") { _, bundle ->
            val uri = bundle.getString("uri")
            val isFinding = bundle.getBoolean("isFinding")
            val isVideo = bundle.getBoolean("isVideo")

            uri?.let {
                val size = getUriSizeInBytes(requireContext(), it)
                if (isVideo) {
                    if (size > 0 && size < 1024 * 1024) {
                        Toast.makeText(requireContext(), "Ukuran video minimal 1 MB (.mp4)", Toast.LENGTH_SHORT).show()
                    } else {
                        videoPath = it
                        addPhotoToList(it, isFinding = false) // Add video to the main media list
                        binding.tvVideoPath.text = "Video direkam"
                        binding.tvVideoPath.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
                    }
                } else {
                    if (size > 0 && size < 100 * 1024) {
                        Toast.makeText(requireContext(), "Ukuran foto minimal 100 KB (.jpg/.png)", Toast.LENGTH_SHORT).show()
                    } else {
                        addPhotoToList(it, isFinding = isFinding)
                    }
                }
                updateProgress()
            }
        }
    }

    private fun setupFormDefaults() {
        if (inspectionId == -1L) {
            binding.etDate.setText("")
            binding.etTime.setText("")
            binding.etInspector.setText("")
        }
        
        binding.rbNoFindings.isChecked = true
        binding.findingDetailsContainer.visibility = View.GONE
    }

    private fun setupRecyclerViews() {
        var itemTouchHelper: ItemTouchHelper? = null

        checklistAdapter = ChecklistItemAdapter(
            onStartDrag = { viewHolder ->
                itemTouchHelper?.startDrag(viewHolder)
            },
            onItemChanged = { position, text, isChecked ->
                if (position >= 0 && position < checklistItems.size) {
                    checklistItems[position] = Pair(text, isChecked)
                    updateProgress()
                }
            },
            onItemEmptyAndLostFocus = { position ->
                if (position >= 0 && position < checklistItems.size) {
                    checklistItems.removeAt(position)
                    checklistAdapter.submitList(checklistItems.toList())
                    updateProgress()
                }
            }
        )
        binding.rvChecklist.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = checklistAdapter
        }
        checklistAdapter.submitList(checklistItems.toList())

        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                if (fromPos < 0 || fromPos >= checklistItems.size || toPos < 0 || toPos >= checklistItems.size) return false

                val temp = checklistItems[fromPos]
                checklistItems[fromPos] = checklistItems[toPos]
                checklistItems[toPos] = temp

                checklistAdapter.submitList(checklistItems.toList())
                checklistAdapter.notifyItemMoved(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun isLongPressDragEnabled(): Boolean {
                return false
            }
        }
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvChecklist)

        photoAdapter = PhotoAdapter(
            onRemoveClick = { position ->
                photos.removeAt(position)
                photoAdapter.submitList(photos.toList())
                updateProgress()
            },
            onItemClick = { path -> showMediaPreview(path) }
        )
        binding.rvPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = photoAdapter
        }
        
        findingPhotoAdapter = PhotoAdapter(
            onRemoveClick = { position ->
                findingPhotos.removeAt(position)
                findingPhotoAdapter.submitList(findingPhotos.toList())
                updateProgress()
            },
            onItemClick = { path -> showMediaPreview(path) }
        )
        binding.rvFindingPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = findingPhotoAdapter
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { handleBackNavigation() }

        binding.locationInputLayout.setEndIconOnClickListener {
            checkLocationPermissionAndGet()
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
            checklistItems.add(Pair("", true))
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

    private fun checkLocationPermissionAndGet() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            requestLocationPermission.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        Toast.makeText(requireContext(), "Mencari lokasi...", Toast.LENGTH_SHORT).show()
        
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                getAddressFromLocation(location.latitude, location.longitude)
            } else {
                Toast.makeText(requireContext(), "Gagal mendapatkan lokasi. Pastikan GPS aktif.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAddressFromLocation(lat: Double, lon: Double) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(lat, lon, 1) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0].getAddressLine(0)
                        activity?.runOnUiThread {
                            binding.etLocation.setText(address)
                            updateProgress()
                        }
                    }
                }
            } else {
                val addresses = geocoder.getFromLocation(lat, lon, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0].getAddressLine(0)
                    binding.etLocation.setText(address)
                    updateProgress()
                }
            }
        } catch (e: Exception) {
            binding.etLocation.setText("$lat, $lon")
            Toast.makeText(requireContext(), "Gagal konversi alamat, menggunakan koordinat", Toast.LENGTH_SHORT).show()
        }
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
        if (!isFinding && photos.size >= 3) {
            Toast.makeText(requireContext(), "Maksimal 3 foto diperbolehkan", Toast.LENGTH_SHORT).show()
            return
        }
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
            val bundle = Bundle().apply {
                putBoolean("isVideoButton", isCapturingVideo)
                putBoolean("isFinding", isCapturingFinding)
            }
            findNavController().navigate(R.id.action_addInspectionFragment_to_cameraFragment, bundle)
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun showVideoOptions() {
        val options = arrayOf("Rekam Video", "Pilih dari Galeri")
        AlertDialog.Builder(requireContext())
            .setTitle("Pilih Video")
            .setItems(options) { _, which ->
                if (which == 0) {
                    isCapturingVideo = true
                    isCapturingFinding = false // Reset finding flag for video
                    checkCameraPermissionAndLaunch()
                } else {
                    pickVideo.launch("video/*")
                }
            }.show()
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
        if (validateInput(status)) {
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

    private fun validateInput(status: SessionStatus): Boolean {
        if (status == SessionStatus.DRAFT) return true

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

        // Validate min 1 photo and 1 video only when finishing
        if (status == SessionStatus.COMPLETED) {
            if (photos.isEmpty()) {
                Toast.makeText(requireContext(), "Minimal 1 foto wajib dilampirkan", Toast.LENGTH_SHORT).show()
                isValid = false
            }
            if (videoPath == null) {
                Toast.makeText(requireContext(), "Video wajib dilampirkan", Toast.LENGTH_SHORT).show()
                isValid = false
            }
        }
        
        if (!isValid) {
            Toast.makeText(requireContext(), "Harap lengkapi semua field wajib (*)", Toast.LENGTH_SHORT).show()
        }
        
        return isValid
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.existingSession.collectLatest { session ->
                        session?.let {
                            binding.etTitle.setText(it.title)
                            binding.etLocation.setText(it.locationName)
                            binding.etInspector.setText(it.inspectorName)
                            binding.etConclusion.setText(it.notes)
                            
                            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                            binding.etDate.setText(dateFormat.format(Date(it.scheduledDate)))
                            binding.etTime.setText(timeFormat.format(Date(it.scheduledDate)))
                            
                            it.reportVideoPath?.let { path ->
                                videoPath = path
                                binding.tvVideoPath.text = "Video dilampirkan"
                                binding.tvVideoPath.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
                            }
                            
                            updateProgress()
                        }
                    }
                }

                launch {
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
    }

    private fun showMediaPreview(path: String) {
        val isVideo = path.endsWith(".mp4") || path.contains("video", ignoreCase = true)
        val dialog = android.app.Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_media_preview)
        
        val imageView = dialog.findViewById<android.widget.ImageView>(R.id.ivPreview)
        val videoView = dialog.findViewById<android.widget.VideoView>(R.id.vvPreview)
        val btnClose = dialog.findViewById<android.widget.ImageButton>(R.id.btnClose)
        val root = dialog.findViewById<View>(R.id.previewRoot)

        // Ensure the dialog window is actually full screen
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // Handle insets for the close button
        ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val density = resources.displayMetrics.density
            val params = btnClose.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = (16 * density).toInt() + systemBars.top
            params.rightMargin = (16 * density).toInt() + systemBars.right
            btnClose.layoutParams = params
            insets
        }

        if (isVideo) {
            imageView.visibility = View.GONE
            videoView.visibility = View.VISIBLE
            
            try {
                videoView.setVideoURI(Uri.parse(path))
                videoView.setOnPreparedListener { mp ->
                    mp.isLooping = true
                    mp.start()
                }
                videoView.setOnErrorListener { _, _, _ ->
                    videoView.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    Glide.with(requireContext()).load(path).into(imageView)
                    true
                }
            } catch (e: Exception) {
                videoView.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                Glide.with(requireContext()).load(path).into(imageView)
            }
        } else {
            imageView.visibility = View.VISIBLE
            videoView.visibility = View.GONE
            Glide.with(requireContext())
                .load(path)
                .into(imageView)
        }

        btnClose.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun isFormDirty(): Boolean {
        val titleText = binding.etTitle.text?.toString() ?: ""
        val locationText = binding.etLocation.text?.toString() ?: ""
        val inspectorText = binding.etInspector.text?.toString() ?: ""
        val conclusionText = binding.etConclusion.text?.toString() ?: ""
        return titleText.isNotBlank() ||
                locationText.isNotBlank() ||
                inspectorText.isNotBlank() ||
                conclusionText.isNotBlank() ||
                photos.isNotEmpty() ||
                videoPath != null
    }

    private fun handleBackNavigation() {
        if (isFormDirty()) {
            AlertDialog.Builder(requireContext())
                .setTitle("Simpan ke Draft?")
                .setMessage("Anda sedang mengisi form inspeksi. Apakah Anda ingin menyimpannya sebagai draft sebelum keluar?")
                .setPositiveButton("Simpan Draft") { _, _ ->
                    saveInspection(SessionStatus.DRAFT)
                }
                .setNegativeButton("Keluar") { _, _ ->
                    findNavController().popBackStack()
                }
                .setNeutralButton("Batal", null)
                .show()
        } else {
            findNavController().popBackStack()
        }
    }

    private fun getUriSizeInBytes(context: Context, uriString: String): Long {
        return try {
            val uri = Uri.parse(uriString)
            if (uri.scheme == "content" || uri.scheme == "android.resource") {
                context.contentResolver.openAssetFileDescriptor(uri, "r")?.use {
                    it.length
                } ?: 0L
            } else {
                val file = java.io.File(uri.path ?: "")
                if (file.exists()) file.length() else 0L
            }
        } catch (e: Exception) {
            0L
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
