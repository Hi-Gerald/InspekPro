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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.inspekpro.R
import com.inspekpro.databinding.FragmentDashboardBinding
import com.inspekpro.ui.viewmodel.AuthViewModel
import com.inspekpro.ui.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var activeInspectionAdapter: ActiveInspectionAdapter
    private lateinit var newFindingAdapter: NewFindingAdapter

    private var currentSelectedTabId = R.id.tabDashboard

    private val requestPermissionLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyWindowInsets()
        setupRecyclerViews()
        setupClickListeners()
        setupBottomNavigation()
        observeViewModel()
        checkAndRequestPermissions()
        setupDateAndGreeting()

        android.util.Log.d("DASHBOARD_DEBUG", "Dashboard dibuat")
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val density = resources.displayMetrics.density
            
            // Handle Bottom Insets
            binding.bottomNavContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = systemBars.bottom
            }
            
            binding.fabAdd.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val baseMargin = (32 * density).toInt()
                bottomMargin = baseMargin + systemBars.bottom
            }

            // Handle Top Insets (Status Bar)
            binding.tvAppName.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val baseMargin = (24 * density).toInt()
                topMargin = baseMargin + systemBars.top
            }
            binding.btnProfile.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val baseMargin = (24 * density).toInt()
                topMargin = baseMargin + systemBars.top
            }
            
            insets
        }
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

    private fun setupDateAndGreeting() {
        val idLocale = Locale("id", "ID")
        val sdf = SimpleDateFormat("EEEE, d MMMM yyyy", idLocale)
        binding.tvDate.text = sdf.format(Date())
        binding.tvUserGreeting.text = "${getGreetingText()}, Budi"
    }

    private fun getGreetingText(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 0..10 -> "Selamat Pagi"
            in 11..14 -> "Selamat Siang"
            in 15..17 -> "Selamat Sore"
            else -> "Selamat Malam"
        }
    }

    private fun checkAndRequestPermissions() {
        val permissions = mutableListOf<String>()

        if (androidx.core.content.ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (androidx.core.content.ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissions.isNotEmpty()) {
            requestPermissionLauncher.launch(permissions.toTypedArray())
        }
    }

    private fun setupClickListeners() {
        // Floating Action Button
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addInspectionFragment)
        }

        // Notification Icon
        binding.btnNotification.setOnClickListener {
            val resId = resources.getIdentifier("action_dashboardFragment_to_notificationFragment", "id", requireContext().packageName)
            if (resId != 0) {
                navigateSafely(resId, "In Progress")
            } else {
                Toast.makeText(requireContext(), "In Progress", Toast.LENGTH_SHORT).show()
            }
        }

        // Firebase Cloud Sync Indicator Icon
        binding.btnCloudSync.setOnClickListener {
            Toast.makeText(requireContext(), "Sinkronisasi Cloud otomatis aktif", Toast.LENGTH_SHORT).show()
        }

        // View All (Lihat Semua) Actions
        binding.btnLihatSemuaInspeksi.setOnClickListener {
            Toast.makeText(requireContext(), "Menampilkan semua sesi...", Toast.LENGTH_SHORT).show()
        }
        binding.btnLihatSemuaTemuan.setOnClickListener {
            Toast.makeText(requireContext(), "Menampilkan semua temuan...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        val clickListener = View.OnClickListener { v ->
            val clickedTabId = v.id
            if (clickedTabId == currentSelectedTabId) return@OnClickListener

            when (clickedTabId) {
                R.id.tabDashboard -> {
                    animateTabTransition(clickedTabId)
                }
                R.id.tabInspeksi -> {
                    animateTabTransition(clickedTabId)
                    findNavController().navigate(R.id.action_dashboardFragment_to_inspectionListFragment)
                }
                R.id.tabLaporan -> {
                    Toast.makeText(requireContext(), "Menu Laporan segera hadir", Toast.LENGTH_SHORT).show()
                }
                R.id.tabAkun -> {
                    animateTabTransition(clickedTabId)
                    findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment)
                }
            }
        }

        binding.tabDashboard.setOnClickListener(clickListener)
        binding.tabInspeksi.setOnClickListener(clickListener)
        binding.tabLaporan.setOnClickListener(clickListener)
        binding.tabAkun.setOnClickListener(clickListener)
    }

    private fun animateTabTransition(newTabId: Int) {
        if (newTabId == currentSelectedTabId) return

        val context = requireContext()
        val grayColor = androidx.core.content.ContextCompat.getColor(context, R.color.text_secondary)
        val blueColor = androidx.core.content.ContextCompat.getColor(context, R.color.primary)

        val tabs = listOf(
            Triple(R.id.tabDashboard, binding.ivTabDashboard, binding.tvTabDashboard),
            Triple(R.id.tabInspeksi, binding.ivTabInspeksi, binding.tvTabInspeksi),
            Triple(R.id.tabLaporan, binding.ivTabLaporan, binding.tvTabLaporan),
            Triple(R.id.tabAkun, binding.ivTabAkun, binding.tvTabAkun)
        )

        for (tab in tabs) {
            val (id, imageView, textView) = tab
            if (id == newTabId) {
                android.animation.ValueAnimator.ofArgb(grayColor, blueColor).apply {
                    duration = 250
                    addUpdateListener { animator ->
                        val color = animator.animatedValue as Int
                        imageView.setColorFilter(color)
                        textView.setTextColor(color)
                    }
                    start()
                }
                textView.setTypeface(null, android.graphics.Typeface.BOLD)
            } else if (id == currentSelectedTabId) {
                android.animation.ValueAnimator.ofArgb(blueColor, grayColor).apply {
                    duration = 250
                    addUpdateListener { animator ->
                        val color = animator.animatedValue as Int
                        imageView.setColorFilter(color)
                        textView.setTextColor(color)
                    }
                    start()
                }
                textView.setTypeface(null, android.graphics.Typeface.NORMAL)
            }
        }

        currentSelectedTabId = newTabId
    }

    private fun navigateSafely(actionId: Int, fallbackText: String) {
        try {
            val navController = findNavController()
            val currentDest = navController.currentDestination
            if (currentDest != null && currentDest.getAction(actionId) != null) {
                navController.navigate(actionId)
            } else {
                Toast.makeText(requireContext(), fallbackText, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), fallbackText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe User session & redirection logic
                launch {
                    authViewModel.activeUser.collectLatest { user ->
                        if (user == null) {
                            val firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
                            if (firebaseAuth.currentUser == null) {
                                if (findNavController().currentDestination?.id == R.id.dashboardFragment) {
                                    findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
                                }
                            }
                        } else {
                            val greeting = getGreetingText()
                            binding.tvUserGreeting.text = "$greeting, ${user.fullName}"
                        }
                    }
                }

                // Observe Active Inspections (filter & take(3) done inside ViewModel)
                launch {
                    viewModel.activeSessions.collectLatest { sessions ->
                        if (sessions.isEmpty()) {
                            binding.rvActiveInspections.visibility = View.GONE
                            binding.tvNoActiveInspections.visibility = View.VISIBLE
                        } else {
                            binding.rvActiveInspections.visibility = View.VISIBLE
                            binding.tvNoActiveInspections.visibility = View.GONE
                            activeInspectionAdapter.submitList(sessions)
                        }
                    }
                }

                // Observe Recent Findings (take(3) done inside ViewModel)
                launch {
                    viewModel.recentFindings.collectLatest { findings ->
                        if (findings.isEmpty()) {
                            binding.tvTemuanBaruHeader.visibility = View.GONE
                            binding.btnLihatSemuaTemuan.visibility = View.GONE
                            binding.rvNewFindings.visibility = View.GONE
                        } else {
                            binding.tvTemuanBaruHeader.visibility = View.VISIBLE
                            binding.btnLihatSemuaTemuan.visibility = View.VISIBLE
                            binding.rvNewFindings.visibility = View.VISIBLE
                            newFindingAdapter.submitList(findings)
                        }
                    }
                }

                // Observe Dashboard stats live from Room (initial state shows 0)
                launch {
                    viewModel.dashboardStats.collectLatest { stats ->
                        if (stats != null) {
                            binding.tvTotalInspeksiVal.text = stats.totalSessions.toString()
                            binding.tvSelesaiVal.text = stats.completedSessions.toString()
                            binding.tvKritisVal.text = stats.totalCritical.toString()
                            binding.tvLaporanVal.text = stats.completedSessions.toString()
                        } else {
                            binding.tvTotalInspeksiVal.text = "0"
                            binding.tvSelesaiVal.text = "0"
                            binding.tvKritisVal.text = "0"
                            binding.tvLaporanVal.text = "0"
                        }
                    }
                }

                // TODO: WeatherRepository integration will be added later by teammate.
                // Currently preparing weather card visual layout with default state.
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
