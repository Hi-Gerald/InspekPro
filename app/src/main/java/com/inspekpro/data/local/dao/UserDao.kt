package com.inspekpro.data.local.dao

import androidx.room.*
import com.inspekpro.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE is_logged_in = 1 LIMIT 1")
    fun getActiveUser(): Flow<UserEntity?>

    @Query("UPDATE users SET is_logged_in = :isLoggedIn WHERE user_id = :userId")
    suspend fun updateLoginStatus(userId: Long, isLoggedIn: Boolean)

    @Query("UPDATE users SET is_logged_in = 0")
    suspend fun clearAllLogins()
}
