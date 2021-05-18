package com.macoev.aadstudyproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.macoev.aadstudyproject.data.dao.UserDao
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.network.UserApi
import kotlinx.coroutines.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class UserRepository(private val dao: UserDao) : Repository<User>, KoinComponent {

    private val api: UserApi by inject()

    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    override fun getAll(): LiveData<List<User>> {
        scope.launch { api.getUser() }
        return dao.findAll()
    }

    override fun getAllByTime(): LiveData<PagedList<User>> =
        dao.findAllByTime().toLiveData(pageSize = 10)

    @KoinApiExtension
    override fun insert(vararg users: User) = scope.launch {
        try {
            api.saveUser(*users)
        } catch (e: Exception) {
        } finally {
            dao.insertOrUpdate(*users)
        }
    }

    override fun delete(user: User) = scope.launch { dao.delete(user) }

    override fun findBy(name: String): LiveData<List<User>> {
        scope.launch { api.getUser(name) }
        return dao.findAllByName(name)
    }

    override fun findByIds(vararg ids: Int): LiveData<List<User>> {
        scope.launch { ids.forEach { api.getUser(it.toString()) } }
        return dao.findByIds(ids)
    }

    override fun deleteAll() = runBlocking { dao.deleteAll() }
}