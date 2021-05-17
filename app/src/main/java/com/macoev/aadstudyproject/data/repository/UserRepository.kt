package com.macoev.aadstudyproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.macoev.aadstudyproject.data.dao.UserDao
import com.macoev.aadstudyproject.data.entity.User
import kotlinx.coroutines.*
import javax.inject.Inject

class UserRepository @Inject constructor(private val dao: UserDao) : Repository<User> {
    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    override fun getAll() = dao.findAll()

    override fun getAllByTime() : LiveData<PagedList<User>> = dao.findAllByTime().toLiveData(pageSize = 10)

    override fun insert(vararg users: User) = scope.launch { dao.insertOrUpdate(*users) }

    override fun delete(user: User) = scope.launch { dao.delete(user) }

    override fun findBy(name: String) = dao.findAllByName(name)

    override fun findByIds(vararg ids: Int) = dao.findByIds(ids)

    override fun deleteAll() = runBlocking { dao.deleteAll() }
}