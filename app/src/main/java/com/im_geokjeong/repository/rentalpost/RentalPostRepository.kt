package com.im_geokjeong.repository.rentalpost

import com.im_geokjeong.model.PostPerson
import com.im_geokjeong.model.PostResponse

class RentalPostRepository(private val remoteDataSource: RentalPostRemoteDataSource) {
    suspend fun postPerson(person: PostPerson): PostResponse {
        return remoteDataSource.postArticle(person)
    }
}