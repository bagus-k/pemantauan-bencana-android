package com.bagus.pemantauanbencana.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProvinceResponse (
    @field:SerializedName("bps_code")
    val bpsCode: String? = null,

    @field:SerializedName("regency_city")
    val regencyCity: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("id_regency")
    val idRegency: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null
): Parcelable
