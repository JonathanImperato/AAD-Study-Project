package com.macoev.roomsample.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE full_name LIKE :fullName LIMIT 1")
    fun findFirstByName(fullName: String): LiveData<User?>


    @Query("SELECT * FROM user WHERE full_name LIKE :fullName")
    fun findByName(fullName: String): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}