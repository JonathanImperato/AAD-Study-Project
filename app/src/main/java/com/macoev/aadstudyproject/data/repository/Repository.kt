package com.macoev.aadstudyproject.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job

interface Repository<E> {
    fun getAll(): LiveData<List<E>>

    fun insert(vararg users: E): Job?

    fun delete(e: E): Job?

    fun findBy(name: String): LiveData<List<E>>

    fun findByIds(vararg ids: Int): LiveData<List<E>>

    fun deleteAll()
}