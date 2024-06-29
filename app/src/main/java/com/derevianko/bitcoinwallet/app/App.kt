package com.derevianko.bitcoinwallet.app

import android.app.Application
import com.derevianko.bitcoinwallet.util.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        AppPreferences.init(applicationContext)
    }
}