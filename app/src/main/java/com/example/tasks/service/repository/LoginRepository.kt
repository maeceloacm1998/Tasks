package com.example.tasks.service.repository

import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.HeaderModel
import com.example.tasks.service.http.services.LoginService
import com.example.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    private val remote = RetrofitClient.createService(LoginService::class.java)

    fun login(email: String, password: String, apiListener: APIListener) {
        val call: Call<HeaderModel> = remote.login(email, password)
        call.enqueue(object : Callback<HeaderModel> {
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    apiListener.onFailure(validation)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }
        })
    }

    fun create(
        name: String,
        email: String,
        password: String,
        apiListener: APIListener
    ) {
        val call: Call<HeaderModel> = remote.registerUser(name, email, password)
        call.enqueue(object : Callback<HeaderModel> {
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    apiListener.onFailure(validation)
                } else {
                    response.body()?.let { apiListener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                apiListener.onFailure(t.message.toString())
            }

        })
    }


}


