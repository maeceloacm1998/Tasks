package com.example.tasks.service.http.services

import com.example.tasks.service.model.PriorityModel
import retrofit2.Call
import retrofit2.http.GET

interface PriorityService {
    @GET("Priority")
    fun getPriority(): Call<List<PriorityModel>>
}