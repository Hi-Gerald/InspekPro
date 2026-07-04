package com.inspekpro.ui

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
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
 * Integrasi Anom: FusedLocationProviderClient untuk fetch koordinat cuaca otomatis saat menyimpan sesi.
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
    private var isDraftSavedAction = false
    private var isDialogShowing = false
    private var currentFilledFields = 0
    private var currentTotalFields = 0
    private var isNavigatingBack = false

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

    private val requestNotificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (!isGranted) {
            Toast.makeText(requireContext(), "Notifikasi dinonaktifkan", Toast.LENGTH_SHORT).show()
        }
    }

    // Activity Results for Media
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            val size = getUriSizeInBytes(requireContext(), it.toString())
            if (size > 100 * 1024) {
                Toast.makeText(requireContext(), "Ukuran foto maksimal 100 KB (.jpg/.png)", Toast.LENGTH_SHORT).show()
            } else {
                addPhotoToList(it.toString(), isFinding = false) 
            }
        }
    }
    
    private val pickFindingImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            val size = getUriSizeInBytes(requireContext(), it.toString())
            if (size > 100 * 1024) {
                Toast.makeText(requireContext(), "Ukuran foto temuan maksimal 100 KB (.jpg/.png)", Toast.LENGTH_SHORT).show()
            } else {
                addPhotoToList(it.toString(), isFinding = true) 
            }
        }
    }

    private val pickVideo = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            val size = getUriSizeInBytes(requireContext(), it.toString())
            if (size > 1024 * 1024) {
                Toast.makeText(requireContext(), "Ukuran video maksimal 1 MB (.mp4)", Toast.LENGTH_SHORT).show()
            } else {
                videoPath = it.toString()
                binding.tvVideoPath.text = getString(R.string.video_attached)
                binding.tvVideoPath.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
                binding.btnVideoDelete.visibility = View.VISIBLE
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
            binding.tvHeaderTitle.text = getString(R.string.edit_inspection)
            viewModel.loadSession(inspectionId)
        }

        applyWindowInsets()
        checkNotificationPermission()
        setupFormDefaults()
        setupRecyclerViews()
        setupClickListeners()
        setupFormWatchers()
        observeViewModel()
        setupFragmentResultListeners()
        updateProgress()
        setupTouchClearFocus(binding.root)
        setupKeyboardDismissOnScroll()

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
        ViewCompat.requestApplyInsets(binding.root)
    }

    private fun setupFragmentResultListeners() {
        setFragmentResultListener("camera_result") { _, bundle ->
            val uri = bundle.getString("uri")
            val isFinding = bundle.getBoolean("isFinding")
            val isVideo = bundle.getBoolean("isVideo")

            uri?.let {
                val size = getUriSizeInBytes(requireContext(), it)
                if (isVideo) {
                    if (size > 1024 * 1024) {
                        Toast.makeText(requireContext(), "Ukuran video maksimal 1 MB (.mp4)", Toast.LENGTH_SHORT).show()
                    } else {
                        videoPath = it
                        binding.tvVideoPath.text = getString(R.string.video_recorded)
                        binding.tvVideoPath.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
                        binding.btnVideoDelete.visibility = View.VISIBLE
                    }
                } else {
                    if (size > 100 * 1024) {
                        Toast.makeText(requireContext(), "Ukuran foto maksimal 100 KB (.jpg/.png)", Toast.LENGTH_SHORT).show()
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
        binding.btnVideoDelete.visibility = View.GONE
    }

    private fun setupRecyclerViews() {
        checklistAdapter = ChecklistItemAdapter(
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

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position >= 0 && position < checklistItems.size) {
                    val deletedItem = checklistItems[position]
                    checklistItems.removeAt(position)
                    checklistAdapter.submitList(checklistItems.toList())
                    updateProgress()

                    com.google.android.material.snackbar.Snackbar.make(
                        binding.root,
                        "Pemeriksaan dihapus",
                        com.google.android.material.snackbar.Snackbar.LENGTH_LONG
                    ).setAction("Undo") {
                        if (position <= checklistItems.size) {
                            checklistItems.add(position, deletedItem)
                            checklistAdapter.submitList(checklistItems.toList())
                            updateProgress()
                        }
                    }.show()
                }
            }

            override fun onChildDraw(
                c: android.graphics.Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX < 0) {
                    val itemView = viewHolder.itemView
                    val density = resources.displayMetrics.density
                    
                    val marginVertical = 4f * density
                    val marginHorizontal = 8f * density
                    val cornerRadius = 12f * density

                    // Paint for rounded rect background
                    val paint = android.graphics.Paint().apply {
                        color = "#E53935".toColorInt() // Elegant Material Red
                        isAntiAlias = true
                    }

                    val rectLeft = itemView.right.toFloat() + dX
                    val rectRight = itemView.right.toFloat() - marginHorizontal

                    if (rectLeft < rectRight) {
                        c.drawRoundRect(
                            rectLeft,
                            itemView.top.toFloat() + marginVertical,
                            rectRight,
                            itemView.bottom.toFloat() - marginVertical,
                            cornerRadius,
                            cornerRadius,
                            paint
                        )

                        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
                        icon?.let {
                            val iconSize = (24 * density).toInt()
                            val iconMargin = (itemView.height - iconSize) / 2
                            val iconTop = itemView.top + iconMargin
                            val iconBottom = iconTop + iconSize
                            
                            // Stationed delete icon position
                            val iconRight = (itemView.right - marginHorizontal - (16 * density)).toInt()
                            val iconLeft = iconRight - iconSize

                            // Fade-in animation based on swipe depth
                            val swipeProgress = Math.min(1f, Math.abs(dX) / (itemView.width / 3f))
                            it.alpha = (swipeProgress * 255).toInt()
                            
                            it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                            it.setTint(android.graphics.Color.WHITE)
                            it.draw(c)
                        }
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
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
        binding.btnBack.setOnClickListener { 
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

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
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    binding.etTime.setText(timeFormat.format(calendar.time))
                    viewModel.scheduledDate.value = calendar.timeInMillis
                    updateProgress()
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
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

        binding.btnSaveDraft.setOnClickListener {
            isDraftSavedAction = true
            injectGpsAndSave(SessionStatus.DRAFT)
        }
        binding.btnFinishInspection.setOnClickListener {
            isDraftSavedAction = false
            injectGpsAndSave(SessionStatus.COMPLETED)
        }
        binding.btnVideoDelete.setOnClickListener {
            videoPath = null
            binding.tvVideoPath.text = getString(R.string.belum_ada_video)
            binding.tvVideoPath.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            binding.btnVideoDelete.visibility = View.GONE
            updateProgress()
        }
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

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
     * Dapatkan koordinat GPS dari perangkat, kirim ke ViewModel, lalu simpan sesi.
     * Ini menggabungkan fitur GPS Anom dengan validasi Billy.
     */
    private fun injectGpsAndSave(status: SessionStatus) {
        if (!validateInput(status)) return

        val hasLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasLocationPermission) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    viewModel.setGpsCoordinates(location.latitude, location.longitude)
                }
                saveInspection(status)
            }.addOnFailureListener {
                saveInspection(status) // Fallback: simpan tanpa GPS
            }
        } else {
            saveInspection(status) // Tidak ada izin lokasi, simpan langsung
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        Toast.makeText(requireContext(), getString(R.string.searching_location), Toast.LENGTH_SHORT).show()
        
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                getAddressFromLocation(location.latitude, location.longitude)
            } else {
                Toast.makeText(requireContext(), getString(R.string.error_location_not_found), Toast.LENGTH_SHORT).show()
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
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lon, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0].getAddressLine(0)
                    binding.etLocation.setText(address)
                    updateProgress()
                }
            }
        } catch (_: Exception) {
            binding.etLocation.setText(getString(R.string.location_coord_format, lat, lon))
            Toast.makeText(requireContext(), getString(R.string.error_address_conversion), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFormWatchers() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { 
                updateProgress() 
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etTitle.addTextChangedListener(watcher)
        binding.etLocation.addTextChangedListener(watcher)
        binding.etDate.addTextChangedListener(watcher)
        binding.etTime.addTextChangedListener(watcher)
        binding.etInspector.addTextChangedListener(watcher)
        binding.etConclusion.addTextChangedListener(watcher)
        binding.etFindingCategory.addTextChangedListener(watcher)
        binding.etPriority.addTextChangedListener(watcher)
        binding.etFindingDescription.addTextChangedListener(watcher)

        // Clear error outline programmatically as user types
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.titleInputLayout.error = null
                binding.titleInputLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.locationInputLayout.error = null
                binding.locationInputLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.dateInputLayout.error = null
                binding.dateInputLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.timeInputLayout.error = null
                binding.timeInputLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etInspector.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.inspectorInputLayout.error = null
                binding.inspectorInputLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etConclusion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.conclusionInputLayout.error = null
                binding.conclusionInputLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etFindingCategory.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.findingCategoryLayout.error = null
                binding.findingCategoryLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etPriority.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.priorityLayout.error = null
                binding.priorityLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etFindingDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.findingDescriptionLayout.error = null
                binding.findingDescriptionLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
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
        if (!isAdded) return
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
        // Base fields (6): Title, Location, Date, Time, Inspector, Conclusion
        // Checklist (1): Considered 1 section
        // Media (2): 1 for Photos, 1 for Video
        // Findings (1): Considered 1 section
        val totalFields = 10 
        var filledFields = 0
        
        if (binding.etTitle.text?.isNotBlank() == true) filledFields++
        if (binding.etLocation.text?.isNotBlank() == true) filledFields++
        if (binding.etDate.text?.isNotBlank() == true) filledFields++
        if (binding.etTime.text?.isNotBlank() == true) filledFields++
        if (binding.etInspector.text?.isNotBlank() == true) filledFields++
        if (binding.etConclusion.text?.isNotBlank() == true) filledFields++

        // Checklist section (1 field)
        if (checklistItems.isNotEmpty()) {
            if (checklistItems.all { it.first.isNotBlank() && it.second }) filledFields++
        }

        // Photos (1 field)
        if (photos.isNotEmpty()) filledFields++
        
        // Video (1 field)
        if (videoPath != null) filledFields++

        // Findings section (1 field)
        if (binding.rbHasFindings.isChecked) {
            val categoryFilled = binding.etFindingCategory.text?.isNotBlank() == true
            val priorityFilled = binding.etPriority.text?.isNotBlank() == true
            val descFilled = binding.etFindingDescription.text?.isNotBlank() == true
            val photosFilled = findingPhotos.isNotEmpty()
            
            if (categoryFilled && priorityFilled && descFilled && photosFilled) filledFields++
        } else if (binding.rbNoFindings.isChecked) {
            // "No Findings" selected counts as "completed" for this section
            filledFields++
        }

        currentFilledFields = filledFields
        currentTotalFields = totalFields
        val percent = ((filledFields.toDouble() / totalFields) * 100).toInt().coerceAtMost(100)
        
        binding.tvBadgeProgressFields.text = getString(R.string.inspection_fields_filled, filledFields, totalFields)
        binding.tvBadgeProgressPercent.text = getString(R.string.progress_percentage, percent)
        binding.circularProgress.setProgress(percent, true)
    }

    private fun saveInspection(status: SessionStatus) {
        updateProgress() // Final sync before saving
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
                manualVideo = videoPath,
                manualTotalItems = currentTotalFields,
                manualPassedItems = currentFilledFields
            )
        }
    }

    private fun validateInput(status: SessionStatus): Boolean {
        if (status == SessionStatus.DRAFT) return true

        var isValid = true
        var firstInvalidView: View? = null

        fun checkField(layout: com.google.android.material.textfield.TextInputLayout, editText: android.widget.EditText) {
            if (editText.text.isNullOrBlank()) {
                layout.error = "Field ini wajib diisi"
                layout.isErrorEnabled = true
                if (isValid) {
                    isValid = false
                    firstInvalidView = layout
                }
            } else {
                layout.error = null
                layout.isErrorEnabled = false
            }
        }

        // 1. Informasi Dasar
        checkField(binding.titleInputLayout, binding.etTitle)
        checkField(binding.locationInputLayout, binding.etLocation)
        checkField(binding.dateInputLayout, binding.etDate)
        checkField(binding.timeInputLayout, binding.etTime)
        checkField(binding.inspectorInputLayout, binding.etInspector)

        // 3. Dokumentasi Foto
        if (photos.isEmpty()) {
            Toast.makeText(requireContext(), "Minimal 1 foto wajib dilampirkan", Toast.LENGTH_SHORT).show()
            if (isValid) {
                isValid = false
                firstInvalidView = binding.cardPhotos
            }
        }

        // 4. Temuan (jika Ya)
        if (binding.rbHasFindings.isChecked) {
            checkField(binding.findingCategoryLayout, binding.etFindingCategory)
            checkField(binding.priorityLayout, binding.etPriority)
            checkField(binding.findingDescriptionLayout, binding.etFindingDescription)
            if (findingPhotos.isEmpty()) {
                Toast.makeText(requireContext(), "Minimal 1 foto temuan wajib dilampirkan", Toast.LENGTH_SHORT).show()
                if (isValid) {
                    isValid = false
                    firstInvalidView = binding.rvFindingPhotos
                }
            }
        }

        // 5. Kesimpulan
        checkField(binding.conclusionInputLayout, binding.etConclusion)

        if (!isValid) {
            firstInvalidView?.let { view ->
                view.requestFocus()
                binding.nestedScrollView.post {
                    binding.nestedScrollView.smoothScrollTo(0, view.top)
                }
            }
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
                            if (it.scheduledDate != 0L) {
                                binding.etDate.setText(dateFormat.format(Date(it.scheduledDate)))
                                binding.etTime.setText(timeFormat.format(Date(it.scheduledDate)))
                            } else {
                                binding.etDate.setText("")
                                binding.etTime.setText("")
                            }
                            
                            it.reportVideoPath?.let { path ->
                                videoPath = path
                                binding.tvVideoPath.text = getString(R.string.video_attached)
                                binding.tvVideoPath.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
                                binding.btnVideoDelete.visibility = View.VISIBLE
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
                                val message = if (isDraftSavedAction) {
                                    "Draft berhasil disimpan."
                                } else {
                                    "Inspeksi berhasil diselesaikan."
                                }

                                com.google.android.material.snackbar.Snackbar.make(
                                    binding.root,
                                    message,
                                    com.google.android.material.snackbar.Snackbar.LENGTH_LONG
                                ).show()

                                viewLifecycleOwner.lifecycleScope.launch {
                                    kotlinx.coroutines.delay(1500)
                                    if (isAdded && !isNavigatingBack) {
                                        binding.btnSaveDraft.isEnabled = true
                                        binding.btnFinishInspection.isEnabled = true
                                        if (isDraftSavedAction) {
                                            isNavigatingBack = true
                                            findNavController().popBackStack()
                                        } else {
                                            isNavigatingBack = true
                                            val navOptions = androidx.navigation.NavOptions.Builder()
                                                .setPopUpTo(R.id.dashboardFragment, false)
                                                .build()
                                            findNavController().navigate(R.id.reportFragment, null, navOptions)
                                        }
                                    }
                                }
                            }
                            is CreateSessionResult.Error -> {
                                binding.btnSaveDraft.isEnabled = true
                                binding.btnFinishInspection.isEnabled = true
                                context?.let {
                                    Toast.makeText(it, "Gagal: ${result.message}", Toast.LENGTH_SHORT).show()
                                }
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
                videoView.setVideoURI(path.toUri())
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
            } catch (_: Exception) {
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
        if (isDialogShowing || isNavigatingBack) return

        if (isFormDirty()) {
            isDialogShowing = true
            AlertDialog.Builder(requireContext())
                .setTitle("Simpan ke Draft?")
                .setMessage("Anda sedang mengisi form inspeksi. Apakah Anda ingin menyimpannya sebagai draft sebelum keluar?")
                .setPositiveButton("Simpan Draft") { _, _ ->
                    isDialogShowing = false
                    isDraftSavedAction = true
                    saveInspection(SessionStatus.DRAFT)
                }
                .setNegativeButton("Keluar") { _, _ ->
                    isDialogShowing = false
                    if (isAdded && !isNavigatingBack) {
                        isNavigatingBack = true
                        findNavController().popBackStack()
                    }
                }
                .setNeutralButton("Batal") { _, _ ->
                    isDialogShowing = false
                }
                .setOnCancelListener {
                    isDialogShowing = false
                }
                .show()
        } else {
            if (isAdded && !isNavigatingBack) {
                isNavigatingBack = true
                findNavController().popBackStack()
            }
        }
    }

    private fun getUriSizeInBytes(context: Context, uriString: String): Long {
        return try {
            val uri = uriString.toUri()
            if (uri.scheme == "content" || uri.scheme == "android.resource") {
                context.contentResolver.openAssetFileDescriptor(uri, "r")?.use {
                    it.length
                } ?: 0L
            } else {
                val file = java.io.File(uri.path ?: "")
                if (file.exists()) file.length() else 0L
            }
        } catch (_: Exception) {
            0L
        }
    }

    private fun setupTouchClearFocus(view: View) {
        // Set up touch listener for non-textbox views to hide keyboard and clear focus
        if (view !is android.widget.EditText) {
            view.setOnTouchListener { v, _ ->
                hideKeyboard()
                v.performClick()
                false
            }
        }

        // If a view group, iterate over children
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                setupTouchClearFocus(child)
            }
        }
    }

    private fun setupKeyboardDismissOnScroll() {
        binding.nestedScrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        val currentFocus = activity?.currentFocus
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            currentFocus.clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}