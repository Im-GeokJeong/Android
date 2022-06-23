package com.im_geokjeong.repository.modify

import com.im_geokjeong.model.Person
import com.im_geokjeong.model.PersonDetailResponse
import com.im_geokjeong.model.PostResponse

class ModifyRepository(private val remoteDataSource: ModifyDataSource) {
    suspend fun getArticleDetail(postId: Int): PersonDetailResponse {
        return remoteDataSource.getArticleDetail(postId)
    }

    suspend fun updateArticle(person: Person): PostResponse {
        return remoteDataSource.updateArticle(person)
    }
}