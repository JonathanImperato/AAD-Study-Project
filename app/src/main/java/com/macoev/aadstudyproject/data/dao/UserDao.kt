package com.macoev.aadstudyproject.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.macoev.aadstudyproject.data.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun findAll(): LiveData<List<User>>

    @Query("SELECT * FROM user ORDER BY time ASC")
    fun findAllByTime(): DataSource.Factory<Int, User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun findByIds(userIds: IntArray): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE full_name LIKE :fullName LIMIT 1")
    fun findFirstByName(fullName: String): LiveData<User?>

    @Query("SELECT * FROM user WHERE full_name LIKE :fullName")
    fun findAllByName(fullName: String): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //AUTO GENERATED IDS WILL CHANGE WHEN ROWS GET REPLACED
    fun insertOrUpdate(vararg users: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()

    fun isEmpty() = findAll().value?.isNullOrEmpty()
}