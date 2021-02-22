package com.macoev.roomsample.data.repository

import androidx.lifecycle.LiveData
import com.macoev.roomsample.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

interface Repository {
    val scope get() =  CoroutineScope(Job() + Dispatchers.IO)

    fun getAllUsers(): LiveData<List<User>>
    fun insert(vararg users: User): Job?
    fun delete(user: User): Job?
    fun findBy(name: String): LiveData<List<User>>
    fun findFirstBy(name: String): LiveData<User?>
    fun findById(id: Int): LiveData<List<User>>
    fun findById(vararg ids: Int): LiveData<List<User>>
    fun deleteAll()
}