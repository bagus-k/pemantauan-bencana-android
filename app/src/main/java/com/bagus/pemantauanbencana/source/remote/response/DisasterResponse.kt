package com.bagus.pemantauanbencana.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisasterResponse(
    @field:SerializedName("area")
    val area: String,

    @field:SerializedName("damage")
    val damage: String,

    @field:SerializedName("level")
    val level: String,

    @field:SerializedName("operator_id")
    val operatorId: String,

    @field:SerializedName("regency_city")
    val regencyCity: String,

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("link")
    val link: String,

    @field:SerializedName("chronology")
    val chronology: String,

    @field:SerializedName("dead")
    val dead: String,

    @field:SerializedName("minor_injuries")
    val minorInjuries: String,

    @field:SerializedName("source")
    val source: String,

    @field:SerializedName("losses")
    val losses: String,

    @field:SerializedName("photos")
    val photos: String,

    @field:SerializedName("eventdate")
    val eventdate: String,

    @field:SerializedName("serious_wound")
    val seriousWound: String,

    @field:SerializedName("id_logs")
    val idLogs: String,

    @field:SerializedName("province")
    val province: String,

    @field:SerializedName("disastertype")
    val disastertype: String,

    @field:SerializedName("response")
    val response: String,

    @field:SerializedName("weather")
    val weather: String,

    @field:SerializedName("missing")
    val missing: String,

    @field:SerializedName("typeid")
    val typeid: String,

    @field:SerializedName("longitude")
    val longitude: String,

    @field:SerializedName("status")
    val status: String
): Parcelable
