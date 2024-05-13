package com.golojodev.apitemplate

import com.golojodev.apitemplate.data.service.ServiceAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.IOException
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class ServiceAPITest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var serviceAPI: ServiceAPI

    @Before
    fun setup() {
// Setup MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockRequestDispatcher()
        mockWebServer.start()
// Setup Retrofit
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                json.asConverterFactory(
                    contentType = "application/json"
                        .toMediaType()
                )
            )
            .build()
        serviceAPI = retrofit.create(ServiceAPI::class.java)
    }

    @Test
    fun `fetchModels() returns a list of cats`() = runTest {
        val response = serviceAPI.fetchData("cute")
        assert(response.isSuccessful)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }
}