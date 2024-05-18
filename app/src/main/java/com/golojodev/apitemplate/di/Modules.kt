package com.golojodev.apitemplate.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.golojodev.apitemplate.data.ModelDatabase
import com.golojodev.apitemplate.data.service.ServiceAPI
import com.golojodev.apitemplate.data.workers.ModelsSyncWorker
import com.golojodev.apitemplate.domain.repositories.ModelRepository
import com.golojodev.apitemplate.domain.repositories.ModelRepositoryImpl
import com.golojodev.apitemplate.domain.repositories.ThemeRepository
import com.golojodev.apitemplate.domain.repositories.ThemeRepositoryImpl
import com.golojodev.apitemplate.domain.usecases.FetchModelsUseCase
import com.golojodev.apitemplate.domain.usecases.GetFavoritesUseCase
import com.golojodev.apitemplate.domain.usecases.GetModelsUseCase
import com.golojodev.apitemplate.domain.usecases.GetThemeUseCase
import com.golojodev.apitemplate.domain.usecases.SaveThemeUseCase
import com.golojodev.apitemplate.domain.usecases.UpdateModelUseCase
import com.golojodev.apitemplate.domain.usecases.UseCaseProvider
import com.golojodev.apitemplate.domain.usecases.UseCaseProviderImpl
import com.golojodev.apitemplate.presentation.viewmodels.MainViewModel
import com.golojodev.apitemplate.presentation.viewmodels.ThemeViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import retrofit2.Retrofit

// TODO: Canvia la URL
const val BASE_URL = "https://api.example.com"

val appModules = module {
    single { MainViewModel(get()) }
    single { ThemeViewModel(get()) }
    single<ModelRepository> { ModelRepositoryImpl(get(), get(), get()) }
    single<ThemeRepository> { ThemeRepositoryImpl(get(), get()) }
    single {
        Retrofit.Builder().addConverterFactory(
            Json.asConverterFactory(contentType = "application/json".toMediaType())
        )
            .baseUrl(BASE_URL)
            .build()
    }
    single { get<Retrofit>().create(ServiceAPI::class.java) }
    single { Dispatchers.IO }
    single {
        Room.databaseBuilder(
            androidContext(),
            ModelDatabase::class.java,
            "model-database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Insert initial row during database creation
                    db.execSQL("INSERT OR REPLACE INTO ThemeState (theme) VALUES ('DEFAULT')")
                }
            })
            .build()
    }
    single { get<ModelDatabase>().modelDao() }
    single { get<ModelDatabase>().themeStateDao() }
    single { FetchModelsUseCase(get()) }
    single { GetModelsUseCase(get()) }
    single { GetThemeUseCase(get()) }
    single { SaveThemeUseCase(get()) }
    single { UpdateModelUseCase(get()) }
    single { GetFavoritesUseCase(get()) }
    single<UseCaseProvider>{ UseCaseProviderImpl(get() ,get() , get(), get(), get(), get()) }
    worker { ModelsSyncWorker(get(), get(), get()) }
}