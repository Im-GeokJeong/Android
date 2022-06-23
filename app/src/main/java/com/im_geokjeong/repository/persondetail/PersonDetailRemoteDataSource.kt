package com.im_geokjeong.repository.persondetail

import com.im_geokjeong.model.ModifyPerson
import com.im_geokjeong.model.PersonDetailResponse
import com.im_geokjeong.model.PostResponse
import com.im_geokjeong.network.ApiClient


class PersonDetailRemoteDataSource(private val api: ApiClient) : PersonDetailDataSource {
    override suspend fun getArticleDetail(postId: Int): PersonDetailResponse {
        return api.getArticleDetail(postId)
    }

    override suspend fun deleteArticle(postId: Int): PostResponse {
        return api.deleteArticle(postId)
    }

    override suspend fun getAuth(modify: ModifyPerson): PostResponse {
        return api.getAuth(modify)
    }
}