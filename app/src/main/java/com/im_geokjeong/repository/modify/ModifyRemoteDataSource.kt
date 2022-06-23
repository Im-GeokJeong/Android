package com.im_geokjeong.repository.modify

import com.im_geokjeong.model.Person
import com.im_geokjeong.model.PersonDetailResponse
import com.im_geokjeong.model.PostResponse
import com.im_geokjeong.network.ApiClient

class ModifyRemoteDataSource(private val api: ApiClient) : ModifyDataSource {
    override suspend fun getArticleDetail(postId: Int): PersonDetailResponse {
        return api.getArticleDetail(postId)
    }

    override suspend fun updateArticle(person: Person): PostResponse {
        return api.updateArticle(person)
    }

}