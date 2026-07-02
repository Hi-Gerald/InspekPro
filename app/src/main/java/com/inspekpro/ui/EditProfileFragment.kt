package com.inspekpro.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
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

@Suppress("SpellCheckingInspection")
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

        setupClickListeners()
        observeViewModel()

        // Handle System Back Press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleCancelOrBack()
        }
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
                // Observe User Session Info
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
                                Glide.with(this@EditProfileFragment)
                                    .load(photoFile)
                                    .circleCrop()
                                    .into(binding.ivEditAvatar)
                            }

                            // Load Existing Company Logo from filesDir
                            val logoFile = File(requireContext().filesDir, "company_logo_${user.userId}.jpg")
                            if (logoFile.exists()) {
                                binding.ivLogoPreview.colorFilter = null
                                Glide.with(this@EditProfileFragment)
                                    .load(logoFile)
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
            text = "Ubah Foto Profil"
            setTextColor(Color.parseColor("#1D2939"))
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
                        "Hapus Foto" -> Color.parseColor("#D92D20")
                        "Batal" -> Color.parseColor("#98A2B3")
                        else -> Color.parseColor("#344054")
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
                    "Ambil Foto (Camera)" -> takeAvatarLauncher.launch(null)
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
        binding.ivLogoPreview.colorFilter = null
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
            val photoFile = File(requireContext().filesDir, "profile_photo_${userId}.jpg")
            try {
                FileOutputStream(photoFile).use { out ->
                    avatarBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (originalUserEntity != null) {
            // If they clicked "Hapus Foto"
            val photoFile = File(requireContext().filesDir, "profile_photo_${userId}.jpg")
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
        if (isDataChanged()) {
            AlertDialog.Builder(requireContext())
                .setTitle("Batalkan Perubahan?")
                .setMessage("Perubahan yang belum disimpan akan hilang.")
                .setPositiveButton("Lanjut Edit", null)
                .setNegativeButton("Tetap Keluar") { _, _ ->
                    findNavController().navigateUp()
                }
                .show()
        } else {
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
        
        val frameLayout = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK)
        }

        val imageView = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.MATRIX
            setImageBitmap(bitmap)
            setOnTouchListener(CropTouchListener())
        }
        frameLayout.addView(imageView)

        val overlayView = object : View(context) {
            private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor("#99000000")
            }
            private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                style = Paint.Style.STROKE
                strokeWidth = 4f
            }

            override fun onDraw(canvas: Canvas) {
                super.onDraw(canvas)
                val w = width.toFloat()
                val h = height.toFloat()
                val radius = minOf(w, h) * 0.4f
                
                canvas.save()
                canvas.clipOutRect(w/2 - radius, h/2 - radius, w/2 + radius, h/2 + radius)
                canvas.drawRect(0f, 0f, w, h, paint)
                canvas.restore()
                
                canvas.drawCircle(w/2, h/2, radius, borderPaint)
            }
        }
        overlayView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        frameLayout.addView(overlayView)

        val tvInstruction = TextView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                topMargin = 96
            }
            text = "Geser & Cubit untuk Mengatur Posisi"
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        }
        frameLayout.addView(tvInstruction)

        val buttonLayout = LinearLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
                bottomMargin = 64
            }
            orientation = LinearLayout.HORIZONTAL
            weightSum = 2f
            setPadding(48, 0, 48, 0)
        }

        val btnCancel = MaterialButton(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                rightMargin = 24
            }
            text = "Batal"
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#44FFFFFF"))
            cornerRadius = 24
        }

        val btnCrop = MaterialButton(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                leftMargin = 24
            }
            text = "Crop"
            setTextColor(Color.WHITE)
            setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
            cornerRadius = 24
        }

        buttonLayout.addView(btnCancel)
        buttonLayout.addView(btnCrop)
        frameLayout.addView(buttonLayout)

        dialog.setView(frameLayout)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnCrop.setOnClickListener {
            try {
                val cropped = cropBitmap(imageView, bitmap)
                binding.ivEditAvatar.setImageBitmap(cropped)
                selectedAvatarBitmap = cropped
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Gagal memotong gambar", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun cropBitmap(imageView: ImageView, originalBitmap: Bitmap): Bitmap {
        val width = imageView.width
        val height = imageView.height
        val cropSize = minOf(width.toFloat(), height.toFloat()) * 0.8f
        val cropLeft = (width - cropSize) / 2
        val cropTop = (height - cropSize) / 2
        
        val croppedBitmap = Bitmap.createBitmap(cropSize.toInt(), cropSize.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(croppedBitmap)
        
        val matrix = Matrix(imageView.imageMatrix)
        matrix.postTranslate(-cropLeft, -cropTop)
        
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.drawBitmap(originalBitmap, matrix, paint)
        
        return Bitmap.createScaledBitmap(croppedBitmap, 360, 360, true)
    }

    private class CropTouchListener : View.OnTouchListener {
        private var mode = 0
        private val matrix = Matrix()
        private val savedMatrix = Matrix()
        private val start = PointF()
        private val mid = PointF()
        private var oldDist = 1f

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            val view = v as? ImageView ?: return false
            val ev = event ?: return false
            when (ev.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    savedMatrix.set(view.imageMatrix)
                    start.set(ev.x, ev.y)
                    mode = 1
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    oldDist = spacing(ev)
                    if (oldDist > 10f) {
                        savedMatrix.set(view.imageMatrix)
                        midPoint(mid, ev)
                        mode = 2
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    mode = 0
                }
                MotionEvent.ACTION_MOVE -> {
                    if (mode == 1) {
                        matrix.set(savedMatrix)
                        matrix.postTranslate(ev.x - start.x, ev.y - start.y)
                    } else if (mode == 2) {
                        val newDist = spacing(ev)
                        if (newDist > 10f) {
                            matrix.set(savedMatrix)
                            val scale = newDist / oldDist
                            matrix.postScale(scale, scale, mid.x, mid.y)
                        }
                    }
                    view.imageMatrix = matrix
                }
            }
            return true
        }

        private fun spacing(event: MotionEvent): Float {
            val x = event.getX(0) - event.getX(1)
            val y = event.getY(0) - event.getY(1)
            return kotlin.math.sqrt(x * x + y * y)
        }

        private fun midPoint(point: PointF, event: MotionEvent) {
            val x = event.getX(0) + event.getX(1)
            val y = event.getY(0) + event.getY(1)
            point.set(x / 2, y / 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
