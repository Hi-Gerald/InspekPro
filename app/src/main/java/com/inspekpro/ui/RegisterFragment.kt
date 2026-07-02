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

    private var isCharCountValid = false
    private var isUppercaseValid = false
    private var isLowercaseValid = false
    private var isNumberValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyWindowInsets()
        setupClickListeners()
        observeViewModel()
        setupPasswordValidation()
    }

    private fun applyWindowInsets() {
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        androidx.core.view.ViewCompat.requestApplyInsets(binding.root)
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

            if (confirmPassword.isEmpty()) {
                binding.confirmPasswordInputLayout.error = "Konfirmasi Password tidak boleh kosong"
                return@setOnClickListener
            } else if (password != confirmPassword) {
                binding.confirmPasswordInputLayout.error = "Password tidak cocok"
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

    private fun setupPasswordValidation() {
        binding.passwordEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s?.toString() ?: ""
                checkPasswordRequirements(password)
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun checkPasswordRequirements(password: String) {
        val charCountValid = password.length >= 8
        if (charCountValid != isCharCountValid) {
            isCharCountValid = charCountValid
            animateRequirement(binding.cardReqCharCount, binding.imgReqCharCount, binding.tvReqCharCount, charCountValid)
        }

        val uppercaseValid = password.any { it.isUpperCase() }
        if (uppercaseValid != isUppercaseValid) {
            isUppercaseValid = uppercaseValid
            animateRequirement(binding.cardReqUppercase, binding.imgReqUppercase, binding.tvReqUppercase, uppercaseValid)
        }

        val lowercaseValid = password.any { it.isLowerCase() }
        if (lowercaseValid != isLowercaseValid) {
            isLowercaseValid = lowercaseValid
            animateRequirement(binding.cardReqLowercase, binding.imgReqLowercase, binding.tvReqLowercase, lowercaseValid)
        }

        val numberValid = password.any { it.isDigit() }
        if (numberValid != isNumberValid) {
            isNumberValid = numberValid
            animateRequirement(binding.cardReqNumber, binding.imgReqNumber, binding.tvReqNumber, numberValid)
        }
    }

    private fun animateRequirement(
        card: com.google.android.material.card.MaterialCardView,
        image: android.widget.ImageView,
        text: android.widget.TextView,
        isValid: Boolean
    ) {
        val context = requireContext()
        val inactiveColor = androidx.core.content.ContextCompat.getColor(context, R.color.text_secondary)
        val activeColor = androidx.core.content.ContextCompat.getColor(context, R.color.status_completed_text)

        val fromColor = if (isValid) inactiveColor else activeColor
        val toColor = if (isValid) activeColor else inactiveColor

        android.animation.ValueAnimator.ofArgb(fromColor, toColor).apply {
            duration = 200
            addUpdateListener { animator ->
                val animatedColor = animator.animatedValue as Int
                card.setStrokeColor(android.content.res.ColorStateList.valueOf(animatedColor))
                text.setTextColor(animatedColor)
            }
            start()
        }

        val targetAlpha = if (isValid) 1f else 0f
        val targetScale = if (isValid) 1f else 0.9f

        image.animate()
            .alpha(targetAlpha)
            .scaleX(targetScale)
            .scaleY(targetScale)
            .setDuration(200)
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
