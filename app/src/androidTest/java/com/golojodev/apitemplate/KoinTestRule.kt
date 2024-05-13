package com.golojodev.apitemplate

import androidx.test.core.app.ApplicationProvider
import com.golojodev.apitemplate.di.appModules
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.mp.KoinPlatform

class KoinTestRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                KoinPlatform.stopKoin()
                startKoin {
                    androidLogger(Level.ERROR)
                    androidContext(ApplicationProvider.getApplicationContext())
                    modules(appModules)
                }
            }
        }
    }
}