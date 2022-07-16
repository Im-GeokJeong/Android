package com.im_geokjeong.model

data class CropResponse(
    val status: Int,
    val message: String,
    val data: List<String>
)
