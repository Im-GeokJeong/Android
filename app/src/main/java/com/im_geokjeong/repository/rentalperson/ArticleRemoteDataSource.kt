package com.im_geokjeong.repository.rentalperson

import com.im_geokjeong.model.PersonResponse
import com.im_geokjeong.network.ApiClient

class ArticleRemoteDataSource(private val api: ApiClient): ArticleDataSource {
    override suspend fun getArticleByTitle(title: String): PersonResponse {
        return api.getArticleByTitle(title)
    }

}