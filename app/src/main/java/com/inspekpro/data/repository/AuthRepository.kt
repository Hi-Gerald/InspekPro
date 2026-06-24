package com.inspekpro.data.repository

import com.inspekpro.data.local.dao.UserDao
import com.inspekpro.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest
import android.util.Log

class AuthRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: UserEntity): Result<Long> {
        return try {
            val existingUser = userDao.getUserByEmail(user.email)
            if (existingUser != null) {
                return Result.failure(Exception("Email sudah terdaftar"))
            }
            val hashedPassword = hashPassword(user.passwordHash)
            val userId = userDao.insertUser(user.copy(passwordHash = hashedPassword))
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Sofia edit (Fix glitch bug, cannot enter the dashboard
    suspend fun loginUser(email: String, password: String): Result<UserEntity> {
        return try {

            Log.d("AUTH_DEBUG", "Cari user: $email")

            val user = userDao.getUserByEmail(email)

            Log.d("AUTH_DEBUG", "User ditemukan = ${user != null}")

            if (user == null) {
                return Result.failure(Exception("Email belum terdaftar"))
            }

            val hashedPassword = hashPassword(password)

            Log.d(
                "AUTH_DEBUG",
                "Password cocok = ${user.passwordHash == hashedPassword}"
            )

            if (user.passwordHash != hashedPassword) {
                return Result.failure(Exception("Password salah"))
            }

            Log.d("AUTH_DEBUG", "Update login status")

            userDao.clearAllLogins()
            userDao.updateLoginStatus(user.userId, true)

            // Sofia Code Fix (Login)
            Log.d(
                "AUTH_DEBUG",
                "LOGIN STATUS UPDATED FOR ${user.userId}"
            )
            Result.success(user.copy(isLoggedIn = true))

        } catch (e: Exception) {
            Log.e("AUTH_DEBUG", "ERROR LOGIN", e)
            Result.failure(e)
        }
    }


    fun getActiveUser(): Flow<UserEntity?> = userDao.getActiveUser()

    suspend fun logoutUser() {
        userDao.clearAllLogins()
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
