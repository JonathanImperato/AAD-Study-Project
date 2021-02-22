package com.macoev.roomsample.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.macoev.roomsample.data.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

class FakeRepository : Repository {

    val data = MutableLiveData<List<User>>()

    init {
        data.value = emptyList()
    }

    override fun getAllUsers(): LiveData<List<User>> {
        return data
    }

    override fun insert(vararg users: User): Job? {
        runBlocking {
            getAllUsers().value?.contains(users) ?: return@runBlocking
            data.value = getAllUsers().value?.plus(users)
        }
        return null
    }

    override fun deleteAll() {
        getAllUsers().value?.forEach { delete(it) }
    }

    override fun delete(user: User): Job? {
        runBlocking {
            data.value = getAllUsers().value?.minus(user)
        }
        return null
    }

    override fun findBy(name: String): LiveData<List<User>> {
        return liveData {
            emit(getAllUsers().value?.filter { it.fullName.contains(name, true) }.orEmpty())
        }
    }

    override fun findFirstBy(name: String): LiveData<User?> {
        return liveData {
            emit(getAllUsers().value?.firstOrNull {
                it.fullName.contains(
                    name,
                    true
                )
            })
        }
    }

    override fun findById(id: Int): LiveData<List<User>> {
        return liveData { emit(getAllUsers().value?.filter { it.id == id }.orEmpty()) }
    }

    override fun findById(vararg ids: Int): LiveData<List<User>> {
        return liveData { emit(getAllUsers().value?.filter { it.id!! in ids }.orEmpty()) }
    }
}