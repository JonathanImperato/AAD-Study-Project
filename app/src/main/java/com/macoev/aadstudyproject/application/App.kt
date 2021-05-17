@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.macoev.aadstudyproject.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
