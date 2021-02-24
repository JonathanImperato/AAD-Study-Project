package com.macoev.aadstudyproject.data.repository

import androidx.lifecycle.LiveData
import com.macoev.aadstudyproject.data.User
import kotlinx.coroutines.Job

interface Repository {
    fun getAllUsers(): LiveData<List<User>>

    fun insert(vararg users: User): Job?

    fun delete(user: User): Job?

    fun findBy(name: String): LiveData<List<User>>

    fun findByIds(vararg ids: Int): LiveData<List<User>>

    fun deleteAll()
}