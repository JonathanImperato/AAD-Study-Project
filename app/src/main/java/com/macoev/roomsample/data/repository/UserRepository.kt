package com.macoev.roomsample.data.repository

import com.macoev.roomsample.data.User
import com.macoev.roomsample.data.UserDao
import kotlinx.coroutines.*

class UserRepository(private val dao: UserDao) : Repository {
    private val scope get() = CoroutineScope(Job() + Dispatchers.IO)

    override fun getAllUsers() = dao.getAll()

    override fun insert(vararg users: User) = scope.launch { dao.insertAll(*users) }

    override fun delete(user: User) = scope.launch { dao.delete(user) }

    override fun findBy(name: String) = dao.findByName(name)

    override fun findByIds(vararg ids: Int) = dao.loadAllByIds(ids)

    override fun deleteAll() = runBlocking { dao.deleteAll() }
}