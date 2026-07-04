package com.inspekpro.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ScaleGestureDetector
import android.view.MotionEvent
import android.view.Gravity
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.inspekpro.R
import com.inspekpro.databinding.FragmentProfileBinding
import com.inspekpro.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyWindowInsets()
        setupClickListeners()
        observeViewModel()

        // Observe profile updated navigation result to show Snackbar
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("profile_updated")
            ?.observe(viewLifecycleOwner) { updated ->
                if (updated == true) {
                    com.google.android.material.snackbar.Snackbar.make(
                        binding.root,
                        "Profile berhasil diperbarui",
                        com.google.android.material.snackbar.Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().currentBackStackEntry?.savedStateHandle?.remove<Boolean>("profile_updated")
                }
            }
    }

    private fun applyWindowInsets() {
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            val density = resources.displayMetrics.density

            v.setPadding(0, systemBars.top, 0, 0)
            binding.bottomNavContainer.setPadding(0, 0, 0, systemBars.bottom)
            binding.fabAdd.layoutParams = (binding.fabAdd.layoutParams as ViewGroup.MarginLayoutParams).apply {
                bottomMargin = (32 * density).toInt() + systemBars.bottom
            }
            insets
        }
        androidx.core.view.ViewCompat.requestApplyInsets(binding.root)
    }

    private fun setupClickListeners() {
        // Edit Profile Navigation
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        // Logout action with Material Alert Dialog
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Keluar dari Akun?")
                .setMessage("Anda harus login kembali untuk menggunakan aplikasi.")
                .setPositiveButton("Keluar") { _, _ ->
                    viewModel.logout()
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        // Bottom Navigation Bar Tab Clicks
        binding.tabDashboard.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }
        binding.tabInspeksi.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_inspectionListFragment)
        }
        binding.tabLaporan.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_reportFragment)
        }
        binding.tabAkun.setOnClickListener {
            // Already on profile screen, no action needed
        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addInspectionFragment)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.activeUser.collectLatest { user ->
                    if (user != null) {
                        // Bind general details
                        binding.tvName.text = user.fullName
                        binding.tvDetailName.text = user.fullName
                        binding.tvDetailEmail.text = user.email
                        binding.tvCompany.text = user.companyName
                        binding.tvDetailCompany.text = user.companyName
                        binding.tvDetailJob.text = "Inspector" // Job title is read-only and always Inspector

                        // Format Registration Date (Bergabung Sejak)
                        val idLocale = Locale("id", "ID")
                        val sdf = SimpleDateFormat("d MMMM yyyy", idLocale)
                        binding.tvDetailJoined.text = sdf.format(Date(user.createdAt))

                        // Load Custom Profile Photo (circular)
                        val photoFile = File(requireContext().filesDir, "profile_photo_${user.userId}.jpg")
                        if (photoFile.exists()) {
                            binding.ivAvatar.setPadding(0, 0, 0, 0)
                            binding.ivAvatar.imageTintList = null
                            Glide.with(this@ProfileFragment)
                                .load(photoFile)
                                .signature(ObjectKey(photoFile.lastModified().toString()))
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .circleCrop()
                                .into(binding.ivAvatar)
                        } else {
                            val paddingPx = (16 * resources.displayMetrics.density).toInt()
                            binding.ivAvatar.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
                            binding.ivAvatar.imageTintList = android.content.res.ColorStateList.valueOf(Color.WHITE)
                            Glide.with(this@ProfileFragment)
                                .load(R.drawable.ic_person)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .circleCrop()
                                .into(binding.ivAvatar)
                        }

                        // Avatar Click Preview (WhatsApp behavior, only if custom photo exists)
                        binding.ivAvatar.setOnClickListener {
                            if (photoFile.exists()) {
                                showPhotoPreview(photoFile)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showPhotoPreview(photoFile: File) {
        val context = requireContext()
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar)

        val rootLayout = RelativeLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK)
            alpha = 0f
            setOnClickListener {
                dismissWithFade(dialog, this)
            }
        }

        // 1. Top Bar Layout (WhatsApp Style)
        val topBar = LinearLayout(context).apply {
            id = View.generateViewId()
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                (56 * resources.displayMetrics.density).toInt()
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_TOP)
                // Add top margin to prevent overlapping system bar/notch area when FLAG_LAYOUT_NO_LIMITS is used
                topMargin = (28 * resources.displayMetrics.density).toInt()
            }
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding((16 * resources.displayMetrics.density).toInt(), 0, (16 * resources.displayMetrics.density).toInt(), 0)
            setOnClickListener {
                // Prevent click on top bar from dismissing
            }
        }

        // Back button (ImageView)
        val btnBack = ImageView(context).apply {
            val size = (40 * resources.displayMetrics.density).toInt()
            layoutParams = LinearLayout.LayoutParams(size, size)
            val padding = (8 * resources.displayMetrics.density).toInt()
            setPadding(padding, padding, padding, padding)
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back))
            setColorFilter(Color.WHITE)
            isClickable = true
            isFocusable = true
            setBackgroundResource(android.R.drawable.list_selector_background)
            setOnClickListener {
                dismissWithFade(dialog, rootLayout)
            }
        }
        topBar.addView(btnBack)

        // Title (TextView)
        val tvTitle = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = (16 * resources.displayMetrics.density).toInt()
            }
            text = "Foto profil"
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        }
        topBar.addView(tvTitle)
        rootLayout.addView(topBar)

        // 2. Centered Zoomable ImageView
        val imageView = ImageView(context).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            ).apply {
                addRule(RelativeLayout.BELOW, topBar.id)
                addRule(RelativeLayout.CENTER_IN_PARENT)
            }
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.FIT_CENTER
            
            Glide.with(this@ProfileFragment)
                .load(photoFile)
                .signature(ObjectKey(photoFile.lastModified().toString()))
                .into(this)

            setOnClickListener {
                // Prevent click on image from dismissing
            }
        }

        val scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            var scaleFactor = 1.0f
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(1.0f, 5.0f)
                imageView.scaleX = scaleFactor
                imageView.scaleY = scaleFactor
                return true
            }
        })

        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
        rootLayout.addView(imageView)

        dialog.setContentView(rootLayout)
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.BLACK))
            // Draw edge-to-edge (behind status bar) while keeping status bar icons visible to prevent layout jumps
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.BLACK
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
            setWindowAnimations(0) // Disable default scale animations
        }

        dialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                dismissWithFade(dialog, rootLayout)
                true
            } else {
                false
            }
        }

        dialog.show()
        rootLayout.animate().alpha(1f).setDuration(200).start()
    }

    private fun dismissWithFade(dialog: Dialog, rootLayout: View) {
        rootLayout.animate()
            .alpha(0f)
            .setDuration(200)
            .withEndAction {
                dialog.dismiss()
            }
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}