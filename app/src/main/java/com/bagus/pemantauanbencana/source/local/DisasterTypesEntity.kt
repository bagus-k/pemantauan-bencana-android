package com.bagus.pemantauanbencana.source.local

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisasterTypesEntity (

    @field:SerializedName("id_disaster")
    val idDisaster: String? = null,

    @field:SerializedName("disastertype")
    val disastertype: String? = null,

    @field:SerializedName("desc")
    val desc: String? = null
): Parcelable