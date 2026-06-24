package com.inspekpro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inspekpro.R
import com.inspekpro.databinding.FragmentProfileBinding
import com.inspekpro.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        observeUser()

        binding.btnLogout.setOnClickListener {

            lifecycleScope.launch {

                authViewModel.logout()

                findNavController().navigate(
                    R.id.action_profileFragment_to_loginFragment
                )
            }
        }
    }

    private fun observeUser() {

        viewLifecycleOwner.lifecycleScope.launch {

            authViewModel.activeUser.collectLatest { user ->

                if (user != null) {

                    binding.tvName.text = user.fullName
                    binding.tvEmail.text = user.email
                    binding.tvCompany.text = user.companyName
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}