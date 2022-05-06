package com.example.tasks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PriorityModel
import com.example.tasks.service.repository.TasksRepository
import com.example.tasks.service.repository.remote.PriorityRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {
    private var mTaskRepository = TasksRepository()
    private var mPriorityRepository = PriorityRepository()

    private var mCreateTask = MutableLiveData<Boolean>().apply {
        value = false
    }
    val createTask: LiveData<Boolean> = mCreateTask

    private var mPriorityList = MutableLiveData<List<PriorityModel>>()
    val priorityList: LiveData<List<PriorityModel>> = mPriorityList

    fun getPriority() {
        mPriorityRepository.getPriority(object : APIListener<List<PriorityModel>> {
            override fun onSuccess(model: List<PriorityModel>) {
                mPriorityList.value = model
            }

            override fun onFailure(message: String) {
                Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun createTask(
        priorityId: Int,
        description: String,
        dueDate: String,
        complete:Boolean
    ) {
        mTaskRepository.createTask(priorityId, description, dueDate, complete, object : APIListener<Boolean> {
            override fun onSuccess(model: Boolean) {
                if (model) {
                    mCreateTask.value = true
                }
            }

            override fun onFailure(message: String) {
                Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show()
            }
        })
    }
}