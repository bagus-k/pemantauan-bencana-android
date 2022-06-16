package com.bagus.pemantauanbencana.source.local

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("first_name")
    val firstName: String,

    @field:SerializedName("last_name")
    val lastName: String,

    @field:SerializedName("avatar")
val avatar: String
): Parcelable
