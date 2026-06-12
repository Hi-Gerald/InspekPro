package com.inspekpro.data.repository

import com.inspekpro.data.local.dao.UserDao
import com.inspekpro.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest

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

    suspend fun loginUser(email: String, password: String): Result<UserEntity> {
        return try {
            val user = userDao.getUserByEmail(email)
                ?: return Result.failure(Exception("Email belum terdaftar"))

            val hashedPassword = hashPassword(password)
            if (user.passwordHash != hashedPassword) {
                return Result.failure(Exception("Password salah"))
            }

            userDao.clearAllLogins()
            userDao.updateLoginStatus(user.userId, true)
            Result.success(user.copy(isLoggedIn = true))
        } catch (e: Exception) {
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
