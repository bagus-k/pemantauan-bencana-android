package com.bagus.pemantauanbencana.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserDataResponse(
    @field:SerializedName("data")
    val data: List<UserResponse>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)
