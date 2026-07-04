package com.inspekpro.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.inspekpro.R
import com.inspekpro.data.local.entity.UserEntity
import com.inspekpro.databinding.FragmentEditProfileBinding
import com.inspekpro.ui.viewmodel.ProfileUpdateResult
import com.inspekpro.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private var originalUserEntity: UserEntity? = null
    private var selectedAvatarBitmap: Bitmap? = null
    private var selectedLogoUri: Uri? = null

    // Pick Avatar from Gallery
    private val pickAvatarLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val bitmap = getBitmapFromUri(it)
            if (bitmap != null) {
                showCropDialog(bitmap)
            } else {
                Toast.makeText(requireContext(), "Gagal membaca gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Capture Avatar from Camera
    private val takeAvatarLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            showCropDialog(bitmap)
        }
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            takeAvatarLauncher.launch(null)
        } else {
            Toast.makeText(requireContext(), R.string.error_camera_permission, Toast.LENGTH_SHORT).show()
        }
    }

    // Pick Logo from Gallery
    private val pickLogoLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            handleCompanyLogoSelection(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyWindowInsets()
        setupClickListeners()
        observeViewModel()

        // Handle System Back Press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleCancelOrBack()
        }
    }

    private fun applyWindowInsets() {
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        androidx.core.view.ViewCompat.requestApplyInsets(binding.root)
    }

    private fun setupClickListeners() {
        // Back Button
        binding.btnBack.setOnClickListener {
            handleCancelOrBack()
        }

        // Cancel Button
        binding.btnCancel.setOnClickListener {
            handleCancelOrBack()
        }

        // Change Avatar (opens bottom sheet option)
        binding.btnChangeAvatar.setOnClickListener {
            showAvatarSelectionBottomSheet()
        }
        binding.ivEditAvatar.setOnClickListener {
            showAvatarSelectionBottomSheet()
        }

        // Upload Company Logo area
        binding.btnUploadLogo.setOnClickListener {
            pickLogoLauncher.launch("image/*")
        }

        // Save Changes
        binding.btnSave.setOnClickListener {
            saveChanges()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe User session Info
                launch {
                    viewModel.activeUser.collectLatest { user ->
                        if (user != null && originalUserEntity == null) {
                            originalUserEntity = user
                            binding.etFullName.setText(user.fullName)
                            binding.etEmail.setText(user.email)
                            binding.etCompanyName.setText(user.companyName)

                             // Load Existing Avatar Photo from filesDir
                             val photoFile = File(requireContext().filesDir, "profile_photo_${user.userId}.jpg")
                             if (photoFile.exists()) {
                                 binding.ivEditAvatar.setPadding(0, 0, 0, 0)
                                 binding.ivEditAvatar.imageTintList = null
                                 Glide.with(this@EditProfileFragment)
                                     .load(photoFile)
                                     .signature(com.bumptech.glide.signature.ObjectKey(photoFile.lastModified().toString()))
                                     .circleCrop()
                                     .into(binding.ivEditAvatar)
                             } else {
                                 val paddingPx = (16 * resources.displayMetrics.density).toInt()
                                 binding.ivEditAvatar.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
                                 binding.ivEditAvatar.imageTintList = android.content.res.ColorStateList.valueOf(Color.WHITE)
                             }

                            // Load Existing Company Logo from filesDir
                            val logoFile = File(requireContext().filesDir, "company_logo_${user.userId}.jpg")
                            if (logoFile.exists()) {
                                binding.ivLogoPreview.imageTintList = null
                                Glide.with(this@EditProfileFragment)
                                    .load(logoFile)
                                    .signature(com.bumptech.glide.signature.ObjectKey(logoFile.lastModified().toString()))
                                    .fitCenter()
                                    .into(binding.ivLogoPreview)
                            }
                        }
                    }
                }

                // Observe Save Profile Update Result
                launch {
                    viewModel.updateResult.collectLatest { result ->
                        when (result) {
                            is ProfileUpdateResult.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.btnSave.isEnabled = false
                                binding.btnSave.text = "" // Hide text while loading
                                binding.btnCancel.isEnabled = false
                            }
                            is ProfileUpdateResult.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.btnSave.isEnabled = true
                                binding.btnSave.text = getString(R.string.btn_save_changes)
                                binding.btnCancel.isEnabled = true

                                // Set navigation result for Snackbar toast in ProfileFragment
                                findNavController().previousBackStackEntry?.savedStateHandle?.set("profile_updated", true)
                                viewModel.resetResult()
                                findNavController().navigateUp()
                            }
                            is ProfileUpdateResult.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.btnSave.isEnabled = true
                                binding.btnSave.text = getString(R.string.btn_save_changes)
                                binding.btnCancel.isEnabled = true

                                Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                                viewModel.resetResult()
                            }
                            is ProfileUpdateResult.Idle -> {
                                binding.progressBar.visibility = View.GONE
                                binding.btnSave.isEnabled = true
                                binding.btnCancel.isEnabled = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showAvatarSelectionBottomSheet() {
        val context = requireContext()
        val dialog = com.google.android.material.bottomsheet.BottomSheetDialog(context)
        
        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 24, 0, 24)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundColor(Color.WHITE)
        }

        val title = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(48, 16, 48, 16)
            }
            text = getString(R.string.btn_change_avatar_desc)
            setTextColor("#1D2939".toColorInt())
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        }
        container.addView(title)

        val options = mutableListOf("Ambil Foto (Camera)", "Pilih dari Gallery")
        
        val userId = originalUserEntity?.userId ?: 0
        val photoFile = File(context.filesDir, "profile_photo_${userId}.jpg")
        if (photoFile.exists() || selectedAvatarBitmap != null) {
            options.add("Hapus Foto")
        }
        options.add("Batal")
        
        for (option in options) {
            val itemView = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = option
                setTextColor(
                    when (option) {
                        "Hapus Foto" -> "#D92D20".toColorInt()
                        "Batal" -> "#98A2B3".toColorInt()
                        else -> "#344054".toColorInt()
                    }
                )
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                setPadding(48, 36, 48, 36)
                setBackgroundResource(android.R.drawable.list_selector_background)
                isClickable = true
                isFocusable = true
            }
            
            itemView.setOnClickListener {
                when (option) {
                    "Ambil Foto (Camera)" -> {
                        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                            takeAvatarLauncher.launch(null)
                        } else {
                            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    }
                    "Pilih dari Gallery" -> pickAvatarLauncher.launch("image/*")
                    "Hapus Foto" -> deleteAvatarPhoto()
                }
                dialog.dismiss()
            }
            container.addView(itemView)
        }

        dialog.setContentView(container)
        dialog.show()
    }

    private fun deleteAvatarPhoto() {
        selectedAvatarBitmap = null
        val paddingPx = (16 * resources.displayMetrics.density).toInt()
        binding.ivEditAvatar.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
        binding.ivEditAvatar.imageTintList = android.content.res.ColorStateList.valueOf(Color.WHITE)
        Glide.with(this)
            .load(R.drawable.ic_person)
            .circleCrop()
            .into(binding.ivEditAvatar)
        
        // If file exists, we will mark it for deletion in saveChanges
        Toast.makeText(requireContext(), "Foto profil dihapus sementara, tekan Simpan untuk menerapkan", Toast.LENGTH_SHORT).show()
    }

    private fun handleCompanyLogoSelection(uri: Uri) {
        val size = getFileSize(uri)
        val type = getFileType(uri)

        // Check Size <= 5MB
        if (size > 5 * 1024 * 1024) {
            Toast.makeText(requireContext(), "Ukuran file maksimal 5 MB", Toast.LENGTH_SHORT).show()
            return
        }

        // Check Format PNG, JPG, JPEG
        val extension = type?.substringAfterLast("/")?.lowercase() ?: ""
        if (extension !in listOf("png", "jpg", "jpeg")) {
            Toast.makeText(requireContext(), "Format file harus PNG, JPG, atau JPEG", Toast.LENGTH_SHORT).show()
            return
        }

        // Set Preview
        selectedLogoUri = uri
        binding.ivLogoPreview.imageTintList = null
        Glide.with(this)
            .load(uri)
            .fitCenter()
            .into(binding.ivLogoPreview)
    }

    private fun saveChanges() {
        val fullName = binding.etFullName.text.toString().trim()
        val companyName = binding.etCompanyName.text.toString().trim()

        if (fullName.isEmpty()) {
            binding.tilFullName.error = "Nama Lengkap tidak boleh kosong"
            return
        } else {
            binding.tilFullName.error = null
        }

        if (companyName.isEmpty()) {
            binding.tilCompanyName.error = "Nama Perusahaan tidak boleh kosong"
            return
        } else {
            binding.tilCompanyName.error = null
        }

        val userId = originalUserEntity?.userId ?: 0

        // Handle Avatar File Saving
        val avatarBitmap = selectedAvatarBitmap
        if (avatarBitmap != null) {
            val tempFile = File(requireContext().filesDir, "profile_photo_${userId}_temp.jpg")
            val photoFile = File(requireContext().filesDir, "profile_photo_${userId}.jpg")
            try {
                if (tempFile.exists()) {
                    tempFile.copyTo(photoFile, overwrite = true)
                    tempFile.delete()
                } else {
                    FileOutputStream(photoFile).use { out ->
                        avatarBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (originalUserEntity != null) {
            // If they clicked "Hapus Foto"
            val photoFile = File(requireContext().filesDir, "profile_photo_${userId}.jpg")
            val tempFile = File(requireContext().filesDir, "profile_photo_${userId}_temp.jpg")
            if (tempFile.exists()) {
                tempFile.delete()
            }
            if (photoFile.exists() && binding.ivEditAvatar.drawable?.constantState == ContextCompat.getDrawable(requireContext(), R.drawable.ic_person)?.constantState) {
                photoFile.delete()
            }
        }

        // Handle Logo File Saving
        // TODO: Logo akan digunakan pada laporan PDF di masa mendatang
        val logoUri = selectedLogoUri
        if (logoUri != null) {
            val logoFile = File(requireContext().filesDir, "company_logo_${userId}.jpg")
            try {
                val inputStream = requireContext().contentResolver.openInputStream(logoUri)
                FileOutputStream(logoFile).use { out ->
                    inputStream?.copyTo(out)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModel.updateProfile(fullName, companyName)
    }

    private fun isDataChanged(): Boolean {
        val originalUser = originalUserEntity ?: return false
        val currentName = binding.etFullName.text.toString().trim()
        val currentCompany = binding.etCompanyName.text.toString().trim()
        return currentName != originalUser.fullName ||
               currentCompany != originalUser.companyName ||
               selectedAvatarBitmap != null ||
               selectedLogoUri != null
    }

    private fun handleCancelOrBack() {
        val userId = originalUserEntity?.userId ?: 0
        val tempFile = File(requireContext().filesDir, "profile_photo_${userId}_temp.jpg")
        if (isDataChanged()) {
            AlertDialog.Builder(requireContext())
                .setTitle("Batalkan Perubahan?")
                .setMessage("Perubahan yang belum disimpan akan hilang.")
                .setPositiveButton("Lanjut Edit", null)
                .setNegativeButton("Tetap Keluar") { _, _ ->
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                    findNavController().navigateUp()
                }
                .show()
        } else {
            if (tempFile.exists()) {
                tempFile.delete()
            }
            findNavController().navigateUp()
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileSize(uri: Uri): Long {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        val sizeIndex = cursor?.getColumnIndex(OpenableColumns.SIZE)
        var size: Long = 0
        if (cursor != null && cursor.moveToFirst()) {
            if (sizeIndex != null && sizeIndex >= 0) {
                size = cursor.getLong(sizeIndex)
            }
        }
        cursor?.close()
        return size
    }

    private fun getFileType(uri: Uri): String? {
        return requireContext().contentResolver.getType(uri)
    }

    private fun showCropDialog(bitmap: Bitmap) {
        val context = requireContext()
        val dialog = AlertDialog.Builder(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen).create()

        val rootLayout = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK)
        }

        val cropView = CropView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setImageBitmap(bitmap)
        }
        rootLayout.addView(cropView)

        val bottomBar = RelativeLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
                bottomMargin = (32 * resources.displayMetrics.density).toInt()
            }
            setPadding(
                (24 * resources.displayMetrics.density).toInt(),
                0,
                (24 * resources.displayMetrics.density).toInt(),
                0
            )
        }

        val btnCancel = TextView(context).apply {
            id = View.generateViewId()
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_START)
                addRule(RelativeLayout.CENTER_VERTICAL)
            }
            text = getString(R.string.btn_cancel)
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setPadding(16, 16, 16, 16)
            isClickable = true
            isFocusable = true
            setBackgroundResource(android.R.drawable.list_selector_background)
            setOnClickListener {
                dialog.dismiss()
            }
        }
        bottomBar.addView(btnCancel)

        val btnRotate = ImageView(context).apply {
            id = View.generateViewId()
            layoutParams = RelativeLayout.LayoutParams(
                (48 * resources.displayMetrics.density).toInt(),
                (48 * resources.displayMetrics.density).toInt()
            ).apply {
                addRule(RelativeLayout.CENTER_IN_PARENT)
            }
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_rotate))
            setPadding(12, 12, 12, 12)
            setColorFilter(Color.WHITE)
            isClickable = true
            isFocusable = true
            setBackgroundResource(android.R.drawable.list_selector_background)
            setOnClickListener {
                cropView.rotateImage()
            }
        }
        bottomBar.addView(btnRotate)

        val btnDone = TextView(context).apply {
            id = View.generateViewId()
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_END)
                addRule(RelativeLayout.CENTER_VERTICAL)
            }
            text = getString(R.string.btn_view)
            setTextColor(ContextCompat.getColor(context, R.color.primary))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setPadding(16, 16, 16, 16)
            isClickable = true
            isFocusable = true
            setBackgroundResource(android.R.drawable.list_selector_background)
            setOnClickListener {
                try {
                    val cropped = cropView.getCroppedBitmap()
                    if (cropped != null) {
                        // Save to temporary file first
                        val userId = originalUserEntity?.userId ?: 0
                        val tempFile = File(context.filesDir, "profile_photo_${userId}_temp.jpg")
                        FileOutputStream(tempFile).use { out ->
                            cropped.compress(Bitmap.CompressFormat.JPEG, 90, out)
                        }

                        // Set bitmap in UI only after successful generation and save to temp file
                        binding.ivEditAvatar.setPadding(0, 0, 0, 0)
                        binding.ivEditAvatar.imageTintList = null
                        binding.ivEditAvatar.setImageBitmap(cropped)
                        selectedAvatarBitmap = cropped
                    } else {
                        Toast.makeText(context, "Gagal memotong gambar", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Gagal memotong gambar", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
        }
        bottomBar.addView(btnDone)

        rootLayout.addView(bottomBar)
        dialog.setView(rootLayout)
        dialog.window?.setWindowAnimations(android.R.style.Animation_Dialog)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
