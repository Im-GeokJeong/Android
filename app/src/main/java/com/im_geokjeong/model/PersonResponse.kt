package com.im_geokjeong.model

data class PersonResponse(
    val status: Int,
    val data: List<Person>
)

data class PersonDetailResponse(
    val status: Int,
    val data: Person
)
