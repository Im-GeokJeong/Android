package com.im_geokjeong

import android.content.Context
import androidx.room.Room
import com.im_geokjeong.database.AppDatabase
import com.im_geokjeong.repository.favorite.FavoriteLocalDataSource
import com.im_geokjeong.repository.favorite.FavoriteRepository

object ServiceLocator {
    private var database: AppDatabase? = null
    private var favoriteRepository : FavoriteRepository? = null

    private fun provideDatabase(applicationContext: Context): AppDatabase {
        return database ?: kotlin.run {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "im-local"
            ).build().also {
                database = it
            }
        }
    }

    fun provideFavoriteRepository(context: Context):FavoriteRepository{
        return  favoriteRepository?:kotlin.run{
            val dao = provideDatabase(context.applicationContext).favoriteDao()
            FavoriteRepository(FavoriteLocalDataSource(dao)).also {
                favoriteRepository=it
            }
        }
    }
}