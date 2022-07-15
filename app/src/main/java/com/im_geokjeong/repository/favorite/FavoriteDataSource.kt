package com.im_geokjeong.repository.favorite

import com.im_geokjeong.model.Favorite

interface FavoriteDataSource {

    suspend fun addFavorite(favorite: Favorite)

    suspend fun getFavorite(): List<Favorite>


}