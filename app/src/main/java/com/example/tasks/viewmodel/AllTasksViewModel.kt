package com.example.tasks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.TasksRepository

class AllTasksViewModel(application: Application) : AndroidViewModel(application) {
    private val mTaskRepository = TasksRepository()

    private var mListAllTask = MutableLiveData<List<TaskModel>>()
    val listAllTask: LiveData<List<TaskModel>> = mListAllTask

    fun listAllTasks() {
        mTaskRepository.listAllTasks(object : APIListener<List<TaskModel>> {
            override fun onSuccess(model: List<TaskModel>) {
                mListAllTask.value = model
            }

            override fun onFailure(message: String) {
                Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show()
            }
        })
    }
}