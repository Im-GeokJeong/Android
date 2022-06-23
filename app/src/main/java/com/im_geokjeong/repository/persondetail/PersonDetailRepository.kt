package com.im_geokjeong.repository.persondetail

import com.im_geokjeong.model.ModifyPerson
import com.im_geokjeong.model.PersonDetailResponse
import com.im_geokjeong.model.PostResponse

class PersonDetailRepository(private val remoteDataSource: PersonDetailDataSource) {
    suspend fun getArticleDetail(postId: Int): PersonDetailResponse {
        return remoteDataSource.getArticleDetail(postId)
    }

    suspend fun deleteArticle(postId: Int): PostResponse {
        return remoteDataSource.deleteArticle(postId)
    }

    suspend fun getAuth(modify: ModifyPerson): PostResponse {
        return remoteDataSource.getAuth(modify)
    }
}