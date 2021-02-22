package com.macoev.roomsample

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.macoev.roomsample.data.AppDatabase
import com.macoev.roomsample.data.UserDao
import com.macoev.roomsample.data.repository.Repository
import com.macoev.roomsample.data.repository.UserRepository

object RepositoryLocator {

    var repository: Repository? = null
        @VisibleForTesting set

    fun get(application: Application) = if (repository != null) repository!! else {
        repository = UserRepository(getDao(application))
        repository!!
    }

    private fun getDao(application: Application): UserDao {
        val db = AppDatabase.getDatabase(application)
        return db.userDao()
    }
}