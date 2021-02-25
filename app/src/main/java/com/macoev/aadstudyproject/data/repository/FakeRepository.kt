package com.macoev.aadstudyproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.macoev.aadstudyproject.data.entity.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

class FakeRepository : Repository<User> {

    private val data = MutableLiveData<List<User>>()

    init {
        data.value = emptyList()
    }

    override fun getAll(): LiveData<List<User>> {
        return data
    }

    override fun insert(vararg users: User): Job? {
        runBlocking {
            data.value = data.value?.plus(users.filterNot { data.value?.contains(it) ?: false })
        }
        return null
    }

    override fun deleteAll() {
        data.value = emptyList()
    }

    override fun delete(user: User): Job? {
        runBlocking {
            data.value = data.value?.minus(user)
        }
        return null
    }

    override fun findBy(name: String): LiveData<List<User>> {
        return liveData {
            emit(data.value?.filter { it.fullName.contains(name, true) }.orEmpty())
        }
    }

    override fun findByIds(vararg ids: Int): LiveData<List<User>> {
        return liveData { emit(data.value?.filter { it.id!! in ids }.orEmpty()) }
    }
}