package com.golojodev.apitemplate

import android.app.Application
import com.golojodev.apitemplate.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class ApiTemplateApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
            workManagerFactory()
        }
    }
}