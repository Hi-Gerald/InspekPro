package com.inspekpro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.inspekpro.databinding.FragmentInspectionListBinding
import com.inspekpro.ui.viewmodel.SessionListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ActiveInspectionAdapter { session ->
            Toast.makeText(requireContext(), "Edit: ${session.title}", Toast.LENGTH_SHORT).show()
            // Here you could navigate to an Edit fragment if needed
        }
        binding.rvInspections.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@InspectionListFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allSessions.collectLatest { sessions ->
                    adapter.submitList(sessions)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
