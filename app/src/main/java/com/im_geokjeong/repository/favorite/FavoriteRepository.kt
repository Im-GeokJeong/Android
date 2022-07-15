package com.im_geokjeong.repository.favorite

import com.im_geokjeong.model.Favorite
import com.im_geokjeong.model.Person
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository(private val localDataSource: FavoriteLocalDataSource,private val ioDispatcher: CoroutineDispatcher=Dispatchers.IO) {

    suspend fun addFavorite(favorite: Favorite){
        withContext(ioDispatcher){
            localDataSource.addFavorite(favorite)
        }
    }

    suspend fun getFavorite():List<Favorite>{
        return withContext(ioDispatcher){
            localDataSource.getFavorite()
        }
    }

}
