package com.im_geokjeong.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.im_geokjeong.model.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}
