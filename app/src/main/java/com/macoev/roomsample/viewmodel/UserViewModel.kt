package com.macoev.roomsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.macoev.roomsample.RepositoryLocator
import com.macoev.roomsample.adapter.UserAdapter
import com.macoev.roomsample.data.User

class UserViewModel(application: Application) : AndroidViewModel(application) {
     val repository = RepositoryLocator.get(application)

    val adapter by lazy { UserAdapter(repository) }

    fun getAllUsers() = repository.getAllUsers()
    fun insert(vararg users: User) = repository.insert(*users)
    fun delete(user: User) = repository.delete(user)
    fun deleteAll() = repository.deleteAll()
    fun findBy(name: String) = repository.findBy(name)
    fun findFirstBy(name: String) = repository.findFirstBy(name)
    fun findById(id: Int) = repository.findById(id)
    fun findById(vararg ids: Int) = repository.findById(*ids)
}