package com.im_geokjeong.repository.persondetail

import com.im_geokjeong.model.PersonDetailResponse

class PersonDetailRepository(private val remoteDataSource: PersonDetailDataSource) {
    suspend fun getArticleDetail(postId: Int): PersonDetailResponse {
        return remoteDataSource.getArticleDetail(postId)
    }
}