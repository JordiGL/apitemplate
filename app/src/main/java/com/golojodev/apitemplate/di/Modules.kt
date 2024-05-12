package com.golojodev.apitemplate.di

import com.golojodev.apitemplate.data.service.ServiceAPI
import com.golojodev.apitemplate.domain.repositories.ModelRepository
import com.golojodev.apitemplate.domain.repositories.ModelRepositoryImpl
import com.golojodev.apitemplate.presentation.viewmodels.ModelViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

// TODO: Canvia la URL
const val BASE_URL = "https://api.example.com"

val appModules = module {
    single { ModelViewModel(get()) }
    single<ModelRepository> { ModelRepositoryImpl(get(), get()) }
    single {
        Retrofit.Builder().addConverterFactory(
            Json.asConverterFactory(contentType = "application/json".toMediaType())
        )
        .baseUrl(BASE_URL)
        .build()
    }
    single { get<Retrofit>().create(ServiceAPI::class.java) }
    single { Dispatchers.IO }
}