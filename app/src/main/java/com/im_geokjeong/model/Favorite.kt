package com.im_geokjeong.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite"
)
class Favorite(
    @PrimaryKey val id: Int,
    val title: String,
    val region: String,
    val price: Int
)