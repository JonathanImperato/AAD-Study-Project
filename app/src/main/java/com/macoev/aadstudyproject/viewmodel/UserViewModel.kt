package com.macoev.aadstudyproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macoev.aadstudyproject.adapter.UserAdapter
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.repository.UserRepository
import com.macoev.aadstudyproject.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class UserViewModel @Inject constructor(  val repository: UserRepository) : ViewModel() {


    var selectedUser = MutableLiveData<Event<User?>>()
        private set

    fun getAll() = repository.getAll()

    fun insert(vararg users: User) = repository.insert(*users)

    fun delete(user: User) = repository.delete(user)

    fun deleteAll() = repository.deleteAll()

    fun findBy(name: String) = repository.findBy(name)

    fun findById(vararg ids: Int) = repository.findByIds(*ids)

    fun login() = viewModelScope.launch { repository.api.login() }
}

