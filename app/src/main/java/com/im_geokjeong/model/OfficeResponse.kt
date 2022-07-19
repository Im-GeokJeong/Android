package com.im_geokjeong.model

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