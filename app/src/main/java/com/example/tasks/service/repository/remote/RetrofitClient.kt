package com.example.tasks.service.repository.remote

import com.example.tasks.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private lateinit var retrofit: Retrofit
        private var BASE_URL = "http://devmasterteam.com/CursoAndroidAPI/"
        private var personKey = ""
        private var tokenKey = ""

        fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(TaskConstants.HEADER.TOKEN_KEY, tokenKey)
                    .addHeader(TaskConstants.HEADER.PERSON_KEY, personKey).build()

                chain.proceed(request)
            }

            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }

        fun <T> createService(service: Class<T>): T {
            return getRetrofitInstance().create(service)
        }

        fun addHeader(token: String, person: String) {
            tokenKey = token
            personKey = person
        }
    }
}