package com.bagus.pemantauanbencana.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisasterUpdateResponse(
    @field:SerializedName("message")
    val message: String? = null,
): Parcelable
