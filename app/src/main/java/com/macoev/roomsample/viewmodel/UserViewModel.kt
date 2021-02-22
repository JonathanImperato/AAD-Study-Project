package com.macoev.roomsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.macoev.roomsample.RepositoryLocator
import com.macoev.roomsample.adapter.UserAdapter
import com.macoev.roomsample.data.User
import com.macoev.roomsample.data.repository.Repository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository = RepositoryLocator.get(application)
    var adapter: UserAdapter = UserAdapter(repository)

    fun getAllUsers() = repository.getAllUsers()

    fun insert(vararg users: User) = repository.insert(*users)

    fun delete(user: User) = repository.delete(user)

    fun deleteAll() = repository.deleteAll()

    fun findBy(name: String) = repository.findBy(name)

    fun findById(vararg ids: Int) = repository.findByIds(*ids)

}