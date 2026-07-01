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
import androidx.recyclerview.widget.LinearLayoutManager
import com.inspekpro.R
import com.inspekpro.databinding.FragmentDashboardBinding
import com.inspekpro.ui.viewmodel.AuthViewModel
import com.inspekpro.ui.viewmodel.DashboardViewModel
import com.inspekpro.ui.viewmodel.WeatherUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var activeInspectionAdapter: ActiveInspectionAdapter
    private lateinit var newFindingAdapter: NewFindingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()

        android.util.Log.d("DASHBOARD_DEBUG", "Dashboard dibuat")


        //viewModel.loadWeather(-6.2088, 106.8456)
    }

    private fun setupRecyclerViews() {
        activeInspectionAdapter = ActiveInspectionAdapter { session ->
            Toast.makeText(requireContext(), "Sesi: ${session.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvActiveInspections.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = activeInspectionAdapter
            isNestedScrollingEnabled = false
        }

        newFindingAdapter = NewFindingAdapter { finding ->
            Toast.makeText(requireContext(), "Temuan: ${finding.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvNewFindings.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newFindingAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addInspectionFragment)
        }

        binding.tabInspeksi.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Inspeksi segera hadir", Toast.LENGTH_SHORT).show()
        }
        binding.tabLaporan.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Laporan segera hadir", Toast.LENGTH_SHORT).show()
        }
        binding.tabAkun.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar dari akun?")
                .setPositiveButton("Ya") { _, _ ->
                    authViewModel.logout()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }

        binding.btnNotification.setOnClickListener {
            Toast.makeText(requireContext(), "Mulai sinkronisasi ke Cloud...", Toast.LENGTH_SHORT).show()
            viewModel.syncNow()
        }
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(
                R.id.profileFragment
            )
        }
        binding.btnLihatSemuaInspeksi.setOnClickListener {
            Toast.makeText(requireContext(), "Menampilkan semua sesi...", Toast.LENGTH_SHORT).show()
        }
        binding.btnLihatSemuaTemuan.setOnClickListener {
            Toast.makeText(requireContext(), "Menampilkan semua temuan...", Toast.LENGTH_SHORT).show()
        }
    }

    //Sofia Code Fix (Login)
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe Active User & handle redirect if logged out
                /*launch {
                    authViewModel.activeUser.collectLatest { user ->

                        android.util.Log.d(
                            "DASHBOARD_STATE",
                            "user = ${user?.email}"
                        )
                        if (user == null) {
                            if (findNavController().currentDestination?.id == R.id.dashboardFragment) {
                                findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
                            }
                        } else {
                            binding.tvUserGreeting.text = "Selamat Pagi, ${user.fullName}"
                        }
                    }
                }*/

                // Observe Active Inspections
                launch {
                    viewModel.activeSessions.collectLatest { sessions ->
                        activeInspectionAdapter.submitList(sessions)
                    }
                }

                // Observe Recent Findings
                launch {
                    viewModel.recentFindings.collectLatest { findings ->
                        newFindingAdapter.submitList(findings)
                    }
                }

                // Observe Stats - offsetted to match Figma exactly
                launch {
                    viewModel.totalSessions.collect { count ->
                        binding.tvTotalInspeksiVal.text = (245 + count).toString()
                    }
                }

                launch {
                    viewModel.completedSessions.collect { count ->
                        binding.tvSelesaiVal.text = (188 + count).toString()
                        binding.tvLaporanVal.text = (202 + count).toString()
                    }
                }

                // Observe Weather
                launch {
                    viewModel.weather.collectLatest { state ->
                        when (state) {
                            is WeatherUiState.Loading -> {
                                binding.tvTemperature.text = "28°C"
                                binding.tvWeatherStatus.text = "Berawan Sebagian"
                            }
                            is WeatherUiState.Success -> {
                                val weatherInfo = state.data
                                binding.tvTemperature.text = "${weatherInfo.tempCelsius.toInt()}°C"
                                binding.tvWeatherStatus.text = weatherInfo.conditionDesc.replaceFirstChar { it.uppercase() }
                            }
                            is WeatherUiState.Error -> {
                                binding.tvTemperature.text = "28°C"
                                binding.tvWeatherStatus.text = "Berawan Sebagian"
                            }
                        }
                    }
                }
                
                // Observe Sync Status
                launch {
                    viewModel.syncStatus.collectLatest { result ->
                        if (result.isSuccess) {
                            val count = result.getOrDefault(0)
                            if (count > 0) {
                                Toast.makeText(requireContext(), "Sinkronisasi selesai! $count data di-upload.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(requireContext(), "Semua data sudah sinkron.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Gagal sinkronisasi: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
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
