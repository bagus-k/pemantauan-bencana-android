package com.bagus.pemantauanbencana.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisasterTypeResponse (

        @field:SerializedName("id_disaster")
        val idDisaster: String? = null,

        @field:SerializedName("disastertype")
        val disastertype: String? = null,

        @field:SerializedName("desc")
        val desc: String? = null
    ): Parcelable