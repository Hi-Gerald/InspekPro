package com.inspekpro.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.inspekpro.data.local.dao.UserDao
import com.inspekpro.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

class AuthRepository(
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun registerUser(user: UserEntity): Result<Long> {
        return try {
            // 1. Cek apakah Firebase menggunakan konfigurasi dummy
            val isDummy = firebaseAuth.app.options.projectId == "dummy-project-id"
            
            if (!isDummy) {
                // Firebase Auth Registration
                val authResult = firebaseAuth.createUserWithEmailAndPassword(user.email, user.passwordHash).await()
                val firebaseUser = authResult.user
                if (firebaseUser == null) {
                    return Result.failure(Exception("Gagal mendaftarkan akun di Firebase"))
                }
            }

            // 2. Room DB Local Caching
            val hashedPassword = hashPassword(user.passwordHash)
            val userId = userDao.insertUser(user.copy(passwordHash = hashedPassword))
            Result.success(userId)
        } catch (e: Exception) {
            Log.e("AUTH_DEBUG", "ERROR REGISTER", e)
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<UserEntity> {
        return try {
            Log.d("AUTH_DEBUG", "Memulai login untuk: $email")
            
            // 1. Cek apakah Firebase menggunakan konfigurasi dummy
            val isDummy = firebaseAuth.app.options.projectId == "dummy-project-id"
            
            var user = userDao.getUserByEmail(email)

            if (!isDummy) {
                // Firebase Auth Login
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (authResult.user == null) {
                    return Result.failure(Exception("Gagal masuk via Firebase"))
                }
                
                // Sinkronisasi otomatis ke lokal jika login Firebase berhasil namun data lokal hilang
                if (user == null) {
                    val newUser = UserEntity(
                        fullName = email.substringBefore("@"),
                        email = email,
                        companyName = "Synced from Firebase",
                        passwordHash = hashPassword(password)
                    )
                    userDao.insertUser(newUser)
                    user = userDao.getUserByEmail(email)
                }
            } else {
                // Autentikasi murni lokal (dummy fallback)
                if (user == null) {
                    return Result.failure(Exception("Email belum terdaftar"))
                }
                val hashedPassword = hashPassword(password)
                if (user.passwordHash != hashedPassword) {
                    return Result.failure(Exception("Password salah"))
                }
            }

            if (user == null) {
                return Result.failure(Exception("Gagal mengambil data akun"))
            }

            userDao.clearAllLogins()
            userDao.updateLoginStatus(user.userId, true)

            Log.d("AUTH_DEBUG", "LOGIN STATUS UPDATED FOR ${user.userId}")
            Result.success(user.copy(isLoggedIn = true))

        } catch (e: Exception) {
            Log.e("AUTH_DEBUG", "ERROR LOGIN", e)
            Result.failure(e)
        }
    }

    fun getActiveUser(): Flow<UserEntity?> = userDao.getActiveUser()

    suspend fun logoutUser() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            Log.e("AUTH_DEBUG", "Error signing out from Firebase", e)
        }
        userDao.clearAllLogins()
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
