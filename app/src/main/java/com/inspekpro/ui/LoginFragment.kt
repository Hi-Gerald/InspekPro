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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.inspekpro.R
import com.inspekpro.databinding.FragmentLoginBinding
import com.inspekpro.ui.viewmodel.AuthResult
import com.inspekpro.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import android.util.Log

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyWindowInsets()
        setupClickListeners()
        observeViewModel()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        androidx.core.view.ViewCompat.requestApplyInsets(binding.root)
    }

    private fun setupClickListeners() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "Email tidak boleh kosong"
                return@setOnClickListener
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailInputLayout.error = "Format email tidak valid"
                return@setOnClickListener
            } else {
                binding.emailInputLayout.error = null
            }

            if (password.isEmpty()) {
                binding.passwordInputLayout.error = "Password tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.passwordInputLayout.error = null
            }

            viewModel.login(email, password)
        }

        binding.registerLinkText.setOnClickListener {
            viewModel.resetResults()
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.forgotPasswordText.setOnClickListener {
            viewModel.resetResults()
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.googleLoginBtn.setOnClickListener {
            handleGoogleSignIn()
        }
    }

    private fun handleGoogleSignIn() {
        val webClientId = getString(R.string.default_web_client_id)
        
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = requireActivity()
                )
                
                val credential = result.credential
                if (credential is com.google.android.libraries.identity.googleid.GoogleIdTokenCredential) {
                    val idToken = credential.idToken
                    viewModel.loginWithGoogle(idToken)
                } else if (credential.type == com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        viewModel.loginWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("AUTH_DEBUG", "Received an invalid google id token response", e)
                        Toast.makeText(requireContext(), "Gagal memverifikasi akun Google", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("AUTH_DEBUG", "Unexpected type of credential")
                    Toast.makeText(requireContext(), "Tipe kredensial tidak valid", Toast.LENGTH_SHORT).show()
                }

            } catch (e: GetCredentialException) {
                Log.e("AUTH_DEBUG", "GetCredentialException: ${e.message}")
                Toast.makeText(requireContext(), "Proses Google Login dibatalkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.activeUser.collectLatest { user ->
                        android.util.Log.d(
                            "LOGIN_STATE",
                            "user = ${user?.email}"
                        )

                        if (user != null) {
                            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                            }
                        }
                    }
                }

                // Login Result Observer
                launch {
                    viewModel.loginResult.collectLatest { result ->
                        when (result) {
                            is AuthResult.Idle -> {
                                binding.loginBtn.isEnabled = true
                                binding.loginBtn.text = "Masuk"
                            }
                            is AuthResult.Loading -> {
                                binding.loginBtn.isEnabled = false
                                binding.loginBtn.text = "Memuat..."
                            }
                            is AuthResult.Success -> {
                                binding.loginBtn.isEnabled = true
                                binding.loginBtn.text = "Masuk"
                                viewModel.resetResults()
                            }
                            is AuthResult.Error -> {
                                binding.loginBtn.isEnabled = true
                                binding.loginBtn.text = "Masuk"
                                Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                                viewModel.resetResults()
                            }
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
