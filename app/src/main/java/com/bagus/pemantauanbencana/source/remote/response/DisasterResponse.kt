package com.bagus.pemantauanbencana.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisasterResponse(
    @field:SerializedName("area")
    val area: String? = null,

    @field:SerializedName("damage")
    val damage: String? = null,

    @field:SerializedName("level")
    val level: String? = null,

    @field:SerializedName("operator_id")
    val operatorId: String? = null,

    @field:SerializedName("regency_city")
    val regencyCity: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("chronology")
    val chronology: String? = null,

    @field:SerializedName("dead")
    val dead: String? = null,

    @field:SerializedName("minor_injuries")
    val minorInjuries: String? = null,

    @field:SerializedName("source")
    val source: String? = null,

    @field:SerializedName("losses")
    val losses: String? = null,

    @field:SerializedName("photos")
    val photos: String? = null,

    @field:SerializedName("eventdate")
    val eventdate: String? = null,

    @field:SerializedName("serious_wound")
    val seriousWound: String? = null,

    @field:SerializedName("id_logs")
    val idLogs: String? = null,

    @field:SerializedName("province")
    val province: String? = null,

    @field:SerializedName("disastertype")
    val disastertype: String? = null,

    @field:SerializedName("response")
    val response: String? = null,

    @field:SerializedName("weather")
    val weather: String? = null,

    @field:SerializedName("missing")
    val missing: String? = null,

    @field:SerializedName("typeid")
    val typeid: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null,

    @field:SerializedName("status")
    val status: String? = null
): Parcelable
