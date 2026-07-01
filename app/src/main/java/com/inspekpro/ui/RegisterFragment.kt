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
import androidx.navigation.fragment.findNavController
import com.inspekpro.R
import com.inspekpro.databinding.FragmentRegisterBinding
import com.inspekpro.ui.viewmodel.AuthResult
import com.inspekpro.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.registerBtn.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val company = binding.companyEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
            val isTermsAccepted = binding.termsCheckbox.isChecked

            if (name.isEmpty()) {
                binding.nameInputLayout.error = "Nama Lengkap tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.nameInputLayout.error = null
            }

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "Email tidak boleh kosong"
                return@setOnClickListener
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailInputLayout.error = "Format email tidak valid"
                return@setOnClickListener
            } else {
                binding.emailInputLayout.error = null
            }

            if (company.isEmpty()) {
                binding.companyInputLayout.error = "Nama Perusahaan tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.companyInputLayout.error = null
            }

            if (password.isEmpty()) {
                binding.passwordInputLayout.error = "Password tidak boleh kosong"
                return@setOnClickListener
            } else if (password.length < 6) {
                binding.passwordInputLayout.error = "Password minimal 6 karakter"
                return@setOnClickListener
            } else {
                binding.passwordInputLayout.error = null
            }

            if (confirmPassword != password) {
                binding.confirmPasswordInputLayout.error = "Konfirmasi password tidak cocok"
                return@setOnClickListener
            } else {
                binding.confirmPasswordInputLayout.error = null
            }

            if (!isTermsAccepted) {
                Toast.makeText(requireContext(), "Anda harus menyetujui syarat & ketentuan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(name, email, company, password)
        }

        binding.loginLinkText.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResult.collectLatest { result ->
                    when (result) {
                        is AuthResult.Idle -> {
                            binding.registerBtn.isEnabled = true
                            binding.registerBtn.text = "Daftar"
                        }
                        is AuthResult.Loading -> {
                            binding.registerBtn.isEnabled = false
                            binding.registerBtn.text = "Mendaftarkan..."
                        }
                        is AuthResult.Success -> {
                            binding.registerBtn.isEnabled = true
                            binding.registerBtn.text = "Daftar"
                            viewModel.resetResults()
                            Toast.makeText(requireContext(), "Pendaftaran berhasil! Silakan masuk.", Toast.LENGTH_LONG).show()
                            findNavController().popBackStack()
                        }
                        is AuthResult.Error -> {
                            binding.registerBtn.isEnabled = true
                            binding.registerBtn.text = "Daftar"
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                            viewModel.resetResults()
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
