package com.inspekpro.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inspekpro.R
import com.inspekpro.data.local.entity.InspectionFindingEntity
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.databinding.FragmentReportDashboardBinding
import com.inspekpro.ui.viewmodel.ProfileViewModel
import com.inspekpro.ui.viewmodel.ReportViewModel
import com.inspekpro.util.PdfGeneratorService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ReportFragment : Fragment() {

    private var _binding: FragmentReportDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var reportAdapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyWindowInsets()
        setupRecyclerView()
        setupSearch()
        setupClickListeners()
        observeViewModel()
    }

    private fun applyWindowInsets() {
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            val density = resources.displayMetrics.density

            binding.tvTitle.layoutParams = (binding.tvTitle.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = (24 * density).toInt() + systemBars.top
            }
            binding.bottomNavContainer.setPadding(0, 0, 0, systemBars.bottom)
            binding.fabAdd.layoutParams = (binding.fabAdd.layoutParams as ViewGroup.MarginLayoutParams).apply {
                bottomMargin = (32 * density).toInt() + systemBars.bottom
            }
            insets
        }
        androidx.core.view.ViewCompat.requestApplyInsets(binding.root)
    }

    private fun setupRecyclerView() {
        reportAdapter = ReportAdapter(
            onItemClick = { session ->
                val bundle = Bundle().apply {
                    putLong("sessionId", session.sessionId)
                }
                findNavController().navigate(R.id.action_reportFragment_to_reportDetailFragment, bundle)
            },
            onPdfClick = { session ->
                downloadAndOpenPdfDirectly(session)
            },
            loadCoverPhoto = { sessionId ->
                val findings = viewModel.getFindingsForSession(sessionId).firstOrNull() ?: emptyList()
                val photoPaths = findings.flatMap { parsePhotoPaths(it.photoPaths) }.distinct()
                if (photoPaths.isNotEmpty()) photoPaths.random() else null
            }
        )

        binding.rvReports.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reportAdapter
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchQuery.value = s?.toString() ?: ""
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupClickListeners() {
        // Filter Button click
        binding.btnFilter.setOnClickListener {
            val filterDialog = ReportFilterBottomSheet(
                initialLocation = viewModel.filterLocation.value,
                initialStartDate = viewModel.filterStartDate.value,
                initialEndDate = viewModel.filterEndDate.value,
                initialFindingStatus = viewModel.filterFindingStatus.value
            ) { loc, start, end, finding ->
                viewModel.filterLocation.value = loc
                viewModel.filterStartDate.value = start
                viewModel.filterEndDate.value = end
                viewModel.filterFindingStatus.value = finding
                updateFilterIndicator()
            }
            filterDialog.show(childFragmentManager, "ReportFilterBottomSheet")
        }

        // Sort Button click
        binding.btnSort.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.btnSort)
            popup.menu.add(0, 1, 0, "Terbaru → Terlama")
            popup.menu.add(0, 2, 1, "Terlama → Terbaru")
            
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> {
                        viewModel.sortOption.value = "newest"
                        true
                    }
                    2 -> {
                        viewModel.sortOption.value = "oldest"
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        // Bottom Navigation Bar click bindings
        binding.tabDashboard.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_dashboardFragment)
        }
        binding.tabInspeksi.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_inspectionListFragment)
        }
        binding.tabAkun.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_profileFragment)
        }
        binding.fabAdd.setOnClickListener {
            val bundle = Bundle().apply { putLong("sessionId", -1L) }
            findNavController().navigate(R.id.action_reportFragment_to_addInspectionFragment, bundle)
        }
    }

    private fun updateFilterIndicator() {
        val loc = viewModel.filterLocation.value
        val start = viewModel.filterStartDate.value
        val end = viewModel.filterEndDate.value
        val status = viewModel.filterFindingStatus.value
        val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

        val parts = mutableListOf<String>()
        if (loc.isNotBlank()) parts.add("Lokasi: $loc")
        if (start != null) parts.add("Mulai: ${dateFormat.format(Date(start))}")
        if (end != null) parts.add("Sampai: ${dateFormat.format(Date(end))}")
        if (status != "All") {
            val statusStr = if (status == "Has Findings") "Ada Temuan" else "Tidak Ada Temuan"
            parts.add("Status: $statusStr")
        }

        if (parts.isNotEmpty()) {
            binding.tvActiveFilters.text = "Filter aktif: " + parts.joinToString(" • ")
            binding.tvActiveFilters.visibility = View.VISIBLE
        } else {
            binding.tvActiveFilters.visibility = View.GONE
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredReports.collectLatest { reports ->
                    binding.tvTotalReports.text = "Total ${reports.size} Laporan"
                    if (reports.isEmpty()) {
                        binding.rvReports.visibility = View.GONE
                        binding.emptyStateContainer.visibility = View.VISIBLE
                    } else {
                        binding.emptyStateContainer.visibility = View.GONE
                        binding.rvReports.visibility = View.VISIBLE
                        reportAdapter.submitList(reports)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sortOption.collectLatest { sort ->
                    binding.tvCurrentSort.text = if (sort == "newest") "Terbaru" else "Terlama"
                }
            }
        }
    }

    private fun downloadAndOpenPdfDirectly(session: InspectionSessionEntity) {
        Toast.makeText(requireContext(), "Membuat PDF Laporan...", Toast.LENGTH_SHORT).show()
        
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Fetch findings
                val findings = viewModel.getFindingsForSession(session.sessionId).first()
                // Fetch summary
                val summary = viewModel.getSessionSummary(session.sessionId).firstOrNull()
                // Fetch active user
                val user = profileViewModel.activeUser.value
                val userId = user?.userId ?: 0L
                val companyName = user?.companyName ?: "InspekPro Client"

                val result = PdfGeneratorService.generateAndOpenReport(
                    context = requireContext(),
                    session = session,
                    summary = summary,
                    findings = findings,
                    userId = userId,
                    companyName = companyName
                )

                if (result.isSuccess) {
                    Toast.makeText(requireContext(), "Laporan PDF berhasil diunduh!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gagal: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun parsePhotoPaths(jsonStr: String): List<String> {
        if (jsonStr.isBlank()) return emptyList()
        return try {
            val jsonArray = org.json.JSONArray(jsonStr)
            val list = mutableListOf<String>()
            for (i in 0 until jsonArray.length()) {
                list.add(jsonArray.getString(i))
            }
            list
        } catch (e: Exception) {
            if (jsonStr.contains(",")) {
                jsonStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
            } else {
                listOf(jsonStr.trim()).filter { it.isNotBlank() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
