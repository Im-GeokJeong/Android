package com.im_geokjeong.repository.modify

import com.im_geokjeong.model.Person
import com.im_geokjeong.model.PersonDetailResponse
import com.im_geokjeong.model.PostResponse

interface ModifyDataSource {
    suspend fun getArticleDetail(postId: Int): PersonDetailResponse

    suspend fun updateArticle(person: Person): PostResponse
}