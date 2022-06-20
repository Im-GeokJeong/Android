package com.im_geokjeong.repository.rentalpost

import com.im_geokjeong.model.PostPerson
import com.im_geokjeong.model.PostResponse
import com.im_geokjeong.network.ApiClient

class RentalPostRemoteDataSource(private val apiClient: ApiClient) : RentalPostDataSource {
    override suspend fun postArticle(person: PostPerson): PostResponse {
        return apiClient.postArticle(person)
    }

}