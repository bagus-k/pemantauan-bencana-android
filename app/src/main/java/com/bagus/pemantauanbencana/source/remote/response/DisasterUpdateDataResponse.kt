package com.bagus.pemantauanbencana.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisasterUpdateDataResponse(
    @field:SerializedName("data")
    val data: List<DisasterUpdateResponse>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
): Parcelable
