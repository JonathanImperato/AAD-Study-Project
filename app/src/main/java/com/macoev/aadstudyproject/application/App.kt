@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.macoev.aadstudyproject.application

import android.app.Application
import com.macoev.aadstudyproject.RepositoryLocator
import com.macoev.aadstudyproject.data.network.UserApi
import com.macoev.aadstudyproject.data.network.getClient
import com.macoev.aadstudyproject.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(viewModelModule, repositoryModule, networkModule))
        }
    }
}

val viewModelModule = module {
    factory { UserViewModel(get()) }
}
val repositoryModule = module {
    factory { RepositoryLocator.getUser(get()) }
}
val networkModule = module {
    factory { UserApi() }
    factory { getClient() }
}