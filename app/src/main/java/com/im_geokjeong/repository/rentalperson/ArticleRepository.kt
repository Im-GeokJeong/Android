package com.im_geokjeong.repository.rentalperson

import com.im_geokjeong.model.PersonResponse

class ArticleRepository(private val remoteDataSource: ArticleRemoteDataSource){
    suspend fun getArticleByTitle(title: String): PersonResponse{
        return remoteDataSource.getArticleByTitle(title)
    }

    suspend fun getArticle():PersonResponse{
        return remoteDataSource.getArticle()
    }
}