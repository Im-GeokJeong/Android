package com.im_geokjeong.model

data class Office(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val streetNameAddress: String,
    val lotNumberAddress: String,
    val latitude: String?,
    val longitude: String?,
    val machines: List<Machine>?
)
