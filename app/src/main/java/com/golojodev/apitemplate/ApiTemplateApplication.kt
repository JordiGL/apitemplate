package com.golojodev.apitemplate

import android.app.Application
import com.golojodev.apitemplate.di.appModules
import org.koin.core.context.startKoin

class ApiTemplateApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}