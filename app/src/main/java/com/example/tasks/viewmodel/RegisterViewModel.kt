package com.example.tasks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.repository.HeaderModel
import com.example.tasks.service.repository.LoginRepository
import com.example.tasks.service.repository.local.SecurityPreferences

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val mSecurityPreferences = SecurityPreferences(getApplication())
    private val mLoginRepository = LoginRepository()

    private var mSucccessCreateUser = MutableLiveData<Boolean>().apply {
        value = false
    }
    val successCreateUser = mSucccessCreateUser

    fun create(name: String, email: String, password: String) {
        mLoginRepository.create(name,email,password,object :APIListener{
            override fun onSuccess(model: HeaderModel) {
                with(mSecurityPreferences){
                    store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                    store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                    store(TaskConstants.SHARED.PERSON_NAME, model.name)
                }

                mSucccessCreateUser.value = true
            }

            override fun onFailure(message: String) {
                Toast.makeText(getApplication(),message,Toast.LENGTH_LONG).show()
            }

        })

    }
}