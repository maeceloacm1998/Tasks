package com.example.tasks.service.model

import com.google.gson.annotations.SerializedName

class HeaderModel {
    @SerializedName("token")
    val token: String = ""

    @SerializedName("personKey")
    val personKey: String = ""

    @SerializedName("name")
    val name: String = ""
}