package com.example.raznarokmobileapp

import android.app.Application
import com.example.raznarokmobileapp.core.di.appModule
import com.example.raznarokmobileapp.core.di.networkModule
import com.example.raznarokmobileapp.guest.di.guestModule
import com.example.raznarokmobileapp.login.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RaznarokMobileApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RaznarokMobileApp)
            modules(appModule, networkModule, loginModule, guestModule)
        }
    }
}