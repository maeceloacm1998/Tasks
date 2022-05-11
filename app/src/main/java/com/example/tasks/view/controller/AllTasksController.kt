package com.example.tasks.view.controller

import com.airbnb.epoxy.EpoxyController
import com.example.tasks.service.model.TaskModel
import com.example.tasks.view.holder.taskHolder

class AllTasksController : EpoxyController() {
    private var mListAllTasks: MutableList<TaskModel> = arrayListOf()

    override fun buildModels() {
        mListAllTasks.forEach {
            taskHolder {
                id(it.id)
                idKey(it.id)
                description(it.description)
                dueDate(it.dueDate)
                image(it.complete)
                priority(it.priorituId)
            }
        }
    }

    fun setData(data: List<TaskModel>) {
        for (task in data) {
            mListAllTasks.add(task)
        }

        requestModelBuild()
    }
}

