package com.im_geokjeong.repository.rentalpost

import com.im_geokjeong.model.PostPerson
import com.im_geokjeong.model.PostResponse

interface RentalPostDataSource {
    suspend fun postArticle(person: PostPerson): PostResponse
}