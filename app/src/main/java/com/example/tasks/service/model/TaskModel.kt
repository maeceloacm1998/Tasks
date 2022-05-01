package com.example.tasks.service.model

import com.google.gson.annotations.SerializedName

class TaskModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("PriorityId")
    val priorituId: Int = 0

    @SerializedName("Description")
    val description: String = ""

    @SerializedName("DueDate")
    val dueDate: String = ""

    @SerializedName("Complete")
    val complete: Boolean = false
}