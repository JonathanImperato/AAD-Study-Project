package com.macoev.roomsample.data.repository

import com.macoev.roomsample.data.User
import com.macoev.roomsample.data.UserDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserRepository(private val dao: UserDao) : Repository {

    override fun getAllUsers() = dao.getAll()
    override fun insert(vararg users: User) = scope.launch { dao.insertAll(*users) }
    override fun delete(user: User) = scope.launch { dao.delete(user) }
    override fun findBy(name: String) = dao.findByName(name)
    override fun findFirstBy(name: String) = dao.findFirstByName(name)
    override fun findById(id: Int) = dao.loadAllByIds(intArrayOf(id))
    override fun findById(vararg ids: Int) = dao.loadAllByIds(ids)
    override fun deleteAll() = runBlocking { dao.deleteAll() }
}