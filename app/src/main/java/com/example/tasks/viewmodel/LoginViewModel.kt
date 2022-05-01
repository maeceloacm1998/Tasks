package com.example.tasks.viewmodel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.repository.HeaderModel
import com.example.tasks.service.repository.LoginRepository
import com.example.tasks.service.repository.local.SecurityPreferences
import com.example.tasks.view.AllTasksFragment

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var mSecurityPreferences = SecurityPreferences(application.applicationContext)
    private var mLoginRepository = LoginRepository()

    private var mLogin = MutableLiveData<Boolean>().apply {
        value = false
    }
    val login:LiveData<Boolean> = mLogin

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        mLoginRepository.login(email, password, object : APIListener {
            override fun onSuccess(model: HeaderModel) {
                with(mSecurityPreferences) {
                    store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                    store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                    store(TaskConstants.SHARED.PERSON_NAME, model.name)
                }

                mLogin.value = true
            }

            override fun onFailure(message: String) {
                Toast.makeText(getApplication(),message,Toast.LENGTH_LONG).show()
            }
        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
        val token = mSecurityPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val personToken = mSecurityPreferences.get(TaskConstants.SHARED.PERSON_KEY)

        if(token != ""  && personToken != ""){
            mLogin.value = true
        }
    }

}