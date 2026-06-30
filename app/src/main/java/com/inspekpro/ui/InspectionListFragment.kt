package com.inspekpro.ui

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inspekpro.R
import com.inspekpro.databinding.FragmentInspectionListBinding
import com.inspekpro.ui.viewmodel.SessionListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class InspectionListFragment : Fragment() {

    private var _binding: FragmentInspectionListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SessionListViewModel by viewModels()
    private lateinit var adapter: ActiveInspectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInspectionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ActiveInspectionAdapter { session ->
            Toast.makeText(requireContext(), "Edit: ${session.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvInspections.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@InspectionListFragment.adapter
        }
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_inspectionListFragment_to_addInspectionFragment)
        }

        binding.tabDashboard.setOnClickListener {
            findNavController().navigate(R.id.action_inspectionListFragment_to_dashboardFragment)
        }

        binding.tabLaporan.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Laporan segera hadir", Toast.LENGTH_SHORT).show()
        }

        binding.tabAkun.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Akun segera hadir", Toast.LENGTH_SHORT).show()
        }

        binding.btnViewCalendar.setOnClickListener {
            openSystemCalendar()
        }

        binding.btnNewSchedule.setOnClickListener {
            findNavController().navigate(R.id.action_inspectionListFragment_to_addInspectionFragment)
        }
    }

    private fun openSystemCalendar() {
        val startMillis: Long = Calendar.getInstance().run {
            set(2026, Calendar.JUNE, 10, 8, 0)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(2026, Calendar.JUNE, 10, 10, 0)
            timeInMillis
        }
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = CalendarContract.CONTENT_URI.buildUpon().appendPath("time").appendPath(startMillis.toString()).build()
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Tidak ada aplikasi kalender ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe Active Sessions for the list
                launch {
                    viewModel.activeSessions.collectLatest { sessions ->
                        adapter.submitList(sessions)
                    }
                }

                // Observe Stats
                launch {
                    viewModel.sessionsByStatus.collectLatest { stats ->
                        binding.tvTotalCount.text = stats.total.toString()
                        binding.tvActiveCount.text = stats.proses.toString()
                        binding.tvCompletedCount.text = stats.selesai.toString()
                        binding.tvDraftCount.text = stats.tertunda.toString()
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
