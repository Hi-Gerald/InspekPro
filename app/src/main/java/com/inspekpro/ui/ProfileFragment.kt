package com.inspekpro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            Toast.makeText(requireContext(), "Menu Inspeksi segera hadir", Toast.LENGTH_SHORT).show()
        }
        binding.tabLaporan.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Laporan segera hadir", Toast.LENGTH_SHORT).show()
        }
        binding.tabAkun.setOnClickListener {
            // Already on profile screen, no action needed
        }
        binding.fabAdd.setOnClickListener {
            Toast.makeText(requireContext(), "Tambah Inspeksi belum tersedia", Toast.LENGTH_SHORT).show()
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
                            Glide.with(this@ProfileFragment)
                                .load(photoFile)
                                .signature(ObjectKey(photoFile.lastModified().toString()))
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .circleCrop()
                                .into(binding.ivAvatar)
                        } else {
                            Glide.with(this@ProfileFragment)
                                .load(R.drawable.ic_person)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .circleCrop()
                                .into(binding.ivAvatar)
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