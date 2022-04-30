package com.example.tasks.service.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        private lateinit var retrofit: Retrofit
        private var BASE_URL = "http://devmasterteam.com/CursoAndroidAPI/"

        fun getRetrofitInstance(): Retrofit {
            val httpCleint = OkHttpClient.Builder()
            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpCleint.build())
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }

        fun <T> createService(service: Class<T>): T {
            return getRetrofitInstance().create(service)
        }
    }
}