package com.im_geokjeong.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.im_geokjeong.model.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    suspend fun favoriteLoad(): List<Favorite>

}