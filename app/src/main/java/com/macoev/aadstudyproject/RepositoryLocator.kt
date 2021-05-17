package com.macoev.aadstudyproject

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.macoev.aadstudyproject.data.AppDatabase
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.network.UserApi
import com.macoev.aadstudyproject.data.repository.Repository
import com.macoev.aadstudyproject.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryLocator {

    var userRepository: Repository<User>? = null
        @VisibleForTesting set

    fun getUser(app: Context, api: UserApi): Repository<User> {
        if (userRepository == null) userRepository = UserRepository(getUserDao(app), api)
        return userRepository!!
    }

    //Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Singleton
    @Provides
    fun getUserDao(@ApplicationContext application: Context) = getDatabase(application).userDao()

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext application: Context) = AppDatabase.getDatabase(application)

}