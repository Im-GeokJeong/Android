package com.im_geokjeong.repository.rentalperson

import com.im_geokjeong.model.PersonResponse

interface PersonDataSource {
    suspend fun getArticle():PersonResponse
}