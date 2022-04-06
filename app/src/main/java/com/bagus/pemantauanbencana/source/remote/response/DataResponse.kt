package com.bagus.pemantauanbencana.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class DataResponse(

	@field:SerializedName("data")
	val data: List<DisasterResponse>
)
