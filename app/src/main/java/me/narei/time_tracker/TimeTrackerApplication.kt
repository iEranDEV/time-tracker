package me.narei.time_tracker

import android.app.Application
import me.narei.time_tracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TimeTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TimeTrackerApplication)
            modules(appModule)
        }
    }

}