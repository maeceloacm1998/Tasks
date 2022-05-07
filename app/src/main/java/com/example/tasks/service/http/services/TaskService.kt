package com.example.tasks.service.http.services

import com.example.tasks.service.model.TaskModel
import retrofit2.Call
import retrofit2.http.*

interface TaskService {
    @GET("Task")
    fun listAllTasks(): Call<List<TaskModel>>

    @GET("Next7Days")
    fun listNext7Days(): Call<List<TaskModel>>

    @GET("Overdue")
    fun listOverdue(): Call<List<TaskModel>>

    @GET("Task/{id}")
    fun especificTask(@Path(value = "id") id: Int): Call<TaskModel>

    @POST("Task")
    @FormUrlEncoded
    fun createTask(
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: String,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @HTTP(method = "PUT", path = "Task", hasBody = true)
    fun updateTask(
        @Field("Id") id: Int,
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: String,
        @Field("Complete") complete: Boolean = false
    ): Call<Boolean>

    @PUT("Task/Undo/{id}")
    fun completeTask(@Path(value = "id") id: Int): Call<Boolean>

    @DELETE("Task/{id}")
    fun deleteTask(@Path(value = "id") id: Int): Call<Boolean>
}