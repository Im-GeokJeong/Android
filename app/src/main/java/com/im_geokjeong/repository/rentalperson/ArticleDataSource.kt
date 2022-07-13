package com.im_geokjeong.repository.rentalperson

import com.im_geokjeong.model.PersonResponse

interface ArticleDataSource {
    suspend fun getArticleByTitle(title: String): PersonResponse
    suspend fun getArticle():PersonResponse
}