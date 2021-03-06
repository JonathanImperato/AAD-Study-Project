package com.macoev.aadstudyproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.macoev.aadstudyproject.adapter.UserAdapter
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.repository.Repository
import com.macoev.aadstudyproject.utils.Event
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
open class UserViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    private val repository: Repository<User> by inject()

    private val tap: (User) -> Unit = { user -> selectedUser.value = Event(user) }

    var adapter: UserAdapter = UserAdapter(repository, tap)

    var selectedUser = MutableLiveData<Event<User?>>()
        private set

    fun getAll() = repository.getAll()

    fun insert(vararg users: User) = repository.insert(*users)

    fun delete(user: User) = repository.delete(user)

    fun deleteAll() = repository.deleteAll()

    fun findBy(name: String) = repository.findBy(name)

    fun findById(vararg ids: Int) = repository.findByIds(*ids)
}

