package com.im_geokjeong.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class OfficeResponse(
    val status: Int,
    val message: String?,
    val data: List<Office>
)

data class OfficeDetailResponse(
    val status: Int,
    val message: String,
    val data: Office
)