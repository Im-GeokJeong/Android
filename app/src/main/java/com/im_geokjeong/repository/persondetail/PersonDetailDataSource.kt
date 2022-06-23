package com.im_geokjeong.repository.persondetail

import com.im_geokjeong.model.ModifyPerson
import com.im_geokjeong.model.PersonDetailResponse
import com.im_geokjeong.model.PostResponse

interface PersonDetailDataSource {
    suspend fun getArticleDetail(postId: Int): PersonDetailResponse

    suspend fun deleteArticle(postId: Int): PostResponse

    suspend fun getAuth(modify: ModifyPerson): PostResponse
}

