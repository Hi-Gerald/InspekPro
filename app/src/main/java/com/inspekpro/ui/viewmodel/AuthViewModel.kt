package com.inspekpro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspekpro.data.local.entity.UserEntity
import com.inspekpro.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val activeUser: StateFlow<UserEntity?> = authRepository.getActiveUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _loginResult = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val loginResult: StateFlow<AuthResult> = _loginResult.asStateFlow()

    private val _registerResult = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val registerResult: StateFlow<AuthResult> = _registerResult.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = AuthResult.Loading
            val result = authRepository.loginUser(email, password)
            result.fold(
                onSuccess = { _loginResult.value = AuthResult.Success },
                onFailure = { _loginResult.value = AuthResult.Error(it.message ?: "Login gagal") }
            )
        }
    }

    fun register(fullName: String, email: String, companyName: String, passwordHash: String) {
        viewModelScope.launch {
            _registerResult.value = AuthResult.Loading
            val user = UserEntity(
                fullName = fullName,
                email = email,
                companyName = companyName,
                passwordHash = passwordHash
            )
            val result = authRepository.registerUser(user)
            result.fold(
                onSuccess = { _registerResult.value = AuthResult.Success },
                onFailure = { _registerResult.value = AuthResult.Error(it.message ?: "Pendaftaran gagal") }
            )
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginResult.value = AuthResult.Loading
            val result = authRepository.googleSignIn(idToken)
            result.fold(
                onSuccess = { _loginResult.value = AuthResult.Success },
                onFailure = { _loginResult.value = AuthResult.Error(it.message ?: "Login Google gagal") }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logoutUser()
        }
    }

    fun resetResults() {
        _loginResult.value = AuthResult.Idle
        _registerResult.value = AuthResult.Idle
    }
}

sealed class AuthResult {
    object Idle : AuthResult()
    object Loading : AuthResult()
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}
