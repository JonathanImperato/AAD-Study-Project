package com.macoev.aadstudyproject

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.macoev.aadstudyproject.data.AppDatabase
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.repository.Repository
import com.macoev.aadstudyproject.data.repository.UserRepository

object RepositoryLocator {

    var userRepository: Repository<User>? = null
        @VisibleForTesting set

    fun getUser(application: Application): Repository<User> {
        if (userRepository == null) userRepository = UserRepository(getUserDao(application))
        return userRepository!!
    }

    private fun getUserDao(application: Application) = getDatabase(application).userDao()

    private fun getDatabase(application: Application) = AppDatabase.getDatabase(application)

}