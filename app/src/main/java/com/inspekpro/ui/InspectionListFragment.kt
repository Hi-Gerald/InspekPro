package com.inspekpro.ui

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class InspectionListFragment : Fragment() {

    private var _binding: FragmentInspectionListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SessionListViewModel by viewModels()
    private lateinit var adapter: ActiveInspectionAdapter

    private val weekDays = mutableListOf<Calendar>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInspectionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendarStrip()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupCalendarStrip() {
        val calendar = Calendar.getInstance()
        // Start from Monday of current week
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        
        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        binding.tvCurrentMonth.text = monthFormat.format(calendar.time)

        val dayNames = arrayOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
        
        weekDays.clear()
        for (i in 0..6) {
            val dayCal = calendar.clone() as Calendar
            weekDays.add(dayCal)
            
            val containerId = resources.getIdentifier("dayContainer$i", "id", requireContext().packageName)
            val dayNameId = resources.getIdentifier("tvDayName$i", "id", requireContext().packageName)
            val dayNumId = resources.getIdentifier("tvDayNum$i", "id", requireContext().packageName)

            val container = binding.root.findViewById<View>(containerId)
            val tvName = binding.root.findViewById<android.widget.TextView>(dayNameId)
            val tvNum = binding.root.findViewById<android.widget.TextView>(dayNumId)

            tvName.text = dayNames[i]
            tvNum.text = dayCal.get(Calendar.DAY_OF_MONTH).toString()

            container.setOnClickListener {
                viewModel.setSelectedDate(dayCal.timeInMillis)
            }
            
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
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
            createNewCalendarEvent()
        }
    }

    private fun createNewCalendarEvent() {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, "Inspeksi Baru")
            putExtra(CalendarContract.Events.DESCRIPTION, "Laporan inspeksi rutin")
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + 3600000)
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Aplikasi kalender tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openSystemCalendar() {
        val startMillis: Long = Calendar.getInstance().run {
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
                // Observe Selected Date to update UI
                launch {
                    viewModel.selectedDateMillis.collectLatest { selectedMillis ->
                        updateCalendarUi(selectedMillis)
                    }
                }

                // Observe Filtered Sessions for the list
                launch {
                    viewModel.filteredSessions.collectLatest { sessions ->
                        adapter.submitList(sessions)
                        
                        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                        val dateStr = sdf.format(Date(viewModel.selectedDateMillis.value))
                        binding.tvSelectedDayStatus.text = "$dateStr ${sessions.size} Inspeksi Dijadwalkan"
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

    private fun updateCalendarUi(selectedMillis: Long) {
        val selectedCal = Calendar.getInstance().apply { timeInMillis = selectedMillis }
        val selectedDay = selectedCal.get(Calendar.DAY_OF_YEAR)
        val selectedYear = selectedCal.get(Calendar.YEAR)

        for (i in 0..6) {
            val dayCal = weekDays[i]
            val isSelected = dayCal.get(Calendar.DAY_OF_YEAR) == selectedDay && dayCal.get(Calendar.YEAR) == selectedYear
            
            val dayNumId = resources.getIdentifier("tvDayNum$i", "id", requireContext().packageName)
            val tvNum = binding.root.findViewById<android.widget.TextView>(dayNumId)

            if (isSelected) {
                tvNum.setBackgroundResource(R.drawable.bg_badge_rounded)
                tvNum.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primary)
                tvNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                tvNum.background = null
                tvNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
