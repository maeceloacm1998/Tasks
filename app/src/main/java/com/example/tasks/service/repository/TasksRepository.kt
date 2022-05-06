package com.example.tasks.service.repository

import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.http.services.TaskService
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksRepository {

    private val remote = RetrofitClient.createService(TaskService::class.java)

    fun listAllTasks(apiListener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = remote.listAllTasks()

        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    apiListener.onFailure(TaskConstants.LIST_ERRORS.ERROR_LOAD_LIST)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }

    fun listNext7Days(apiListener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = remote.listNext7Days()

        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    apiListener.onFailure(TaskConstants.LIST_ERRORS.ERROR_LOAD_LIST)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }

    fun listOverdue(apiListener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = remote.listOverdue()

        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    apiListener.onFailure(TaskConstants.LIST_ERRORS.ERROR_LOAD_LIST)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }

    fun especificTask(id: Int, apiListener: APIListener<TaskModel>) {
        val call: Call<TaskModel> = remote.especificTask(id)

        call.enqueue(object : Callback<TaskModel> {
            override fun onResponse(call: Call<TaskModel>, response: Response<TaskModel>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    apiListener.onFailure(validation)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<TaskModel>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }

    fun createTask(
        priorityId: Int,
        description: String,
        dueDate: String,
        complete:Boolean,
        apiListener: APIListener<Boolean>
    ) {
        val call: Call<Boolean> = remote.createTask(priorityId, description, dueDate, complete)

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    apiListener.onFailure(validation)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }

        })
    }

    fun updateTask(
        id: Int,
        priorityId: Int,
        description: String,
        dueDate: String,
        apiListener: APIListener<Boolean>
    ) {
        val call: Call<Boolean> = remote.updateTask(id, priorityId, description, dueDate)

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    apiListener.onFailure(validation)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }

    fun completeTask(id: Int, apiListener: APIListener<Boolean>) {
        val call: Call<Boolean> = remote.completeTask(id)

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    apiListener.onFailure(validation)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }

    fun deleteTask(id: Int, apiListener: APIListener<Boolean>) {
        val call: Call<Boolean> = remote.deleteTask(id)

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    apiListener.onFailure(validation)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }
}
