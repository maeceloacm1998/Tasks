package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.repository.HeaderModel
import com.example.tasks.service.repository.LoginRepository
import com.example.tasks.service.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var mSecurityPreferences = SecurityPreferences(application.applicationContext)
    private var mLoginRepository = LoginRepository()

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        mLoginRepository.login(email,password, object : APIListener{
            override fun onSuccess(model: HeaderModel) {
                with(mSecurityPreferences) {
                    store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                    store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                    store(TaskConstants.SHARED.PERSON_NAME, model.name)
                }

            }

            override fun onFailure(message: String) {
                val s = ""
            }
        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}