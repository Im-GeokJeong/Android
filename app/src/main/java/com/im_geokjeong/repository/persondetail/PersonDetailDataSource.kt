package com.im_geokjeong.repository.persondetail

import com.im_geokjeong.model.PersonDetailResponse

interface PersonDetailDataSource {
    suspend fun getArticleDetail(postId: Int): PersonDetailResponse
}

