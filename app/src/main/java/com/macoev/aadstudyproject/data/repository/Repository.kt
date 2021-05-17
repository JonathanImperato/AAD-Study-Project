package com.macoev.aadstudyproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.macoev.aadstudyproject.viewmodel.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import kotlinx.coroutines.Job

interface Repository<E> {

    fun getAll(): LiveData<List<E>>

    fun insert(vararg users: E): Job?

    fun delete(e: E): Job?

    fun findBy(name: String): LiveData<List<E>>

    fun findByIds(vararg ids: Int): LiveData<List<E>>

    fun deleteAll()

    fun getAllByTime() : LiveData<PagedList<E>>? {  return null  }
}