package com.im_geokjeong.model

data class PostPerson(
    val title:String,
    val content:String,
    val phoneNumber:String,
    val qualification: Boolean,
    val region:String,
    val price:Int,
    val pin:String
)
