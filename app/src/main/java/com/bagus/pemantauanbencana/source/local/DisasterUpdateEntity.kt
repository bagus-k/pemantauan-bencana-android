package com.bagus.pemantauanbencana.source.local

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisasterUpdateEntity(
    @field:SerializedName("message")
    val message: String? = null,
): Parcelable

