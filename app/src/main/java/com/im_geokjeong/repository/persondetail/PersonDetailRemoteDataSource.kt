package com.im_geokjeong.repository.persondetail

import com.im_geokjeong.model.PersonDetailResponse
import com.im_geokjeong.network.ApiClient


class PersonDetailRemoteDataSource(private val api: ApiClient) : PersonDetailDataSource {
    override suspend fun getArticleDetail(postId: Int): PersonDetailResponse {
        return api.getArticleDetail(postId)
    }
}