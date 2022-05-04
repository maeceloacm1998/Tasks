package com.example.tasks.service.repository.remote

import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.http.services.PriorityService
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PriorityModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository {
    private val remote = RetrofitClient.createService(PriorityService::class.java)

    fun getPriority(apiListener: APIListener<List<PriorityModel>>) {
        val call: Call<List<PriorityModel>> = remote.getPriority()

        call.enqueue(object : Callback<List<PriorityModel>> {
            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    apiListener.onFailure(TaskConstants.LIST_ERRORS.ERROR_LOAD_LIST)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }
}