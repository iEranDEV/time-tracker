package me.narei.time_tracker.di

import androidx.room.Room
import me.narei.time_tracker.data.AppDatabase
import me.narei.time_tracker.data.category.CategoryPreferencesManager
import me.narei.time_tracker.ui.screens.settings.SettingsViewModel
import me.narei.time_tracker.ui.screens.time_list.TimeListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "time_tracker_database"
        ).build()
    }

    single { get<AppDatabase>().timeEntryDao() }
    single { CategoryPreferencesManager(androidContext()) }

    viewModel { TimeListViewModel(timeEntryDao = get(), categoryPreferencesManager = get()) }
    viewModel { SettingsViewModel(timeEntryDao = get(), categoryPreferencesManager = get()) }

}