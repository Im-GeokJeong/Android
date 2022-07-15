package com.im_geokjeong.repository.favorite

import com.im_geokjeong.database.FavoriteDao
import com.im_geokjeong.model.Favorite

class FavoriteLocalDataSource(private val dao: FavoriteDao) : FavoriteDataSource {
    override suspend fun addFavorite(favorite: Favorite) {
        dao.insert(favorite)
    }

    override suspend fun getFavorite(): List<Favorite> {
        return dao.favoriteLoad()
    }

}