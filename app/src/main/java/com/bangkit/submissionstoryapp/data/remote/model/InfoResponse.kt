package com.bangkit.submissionstoryapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class InfoResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
