package com.macoev.aadstudyproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.macoev.aadstudyproject.adapter.UserAdapter
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.repository.Repository
import com.macoev.aadstudyproject.data.repository.UserRepository
import com.macoev.aadstudyproject.utils.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

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

