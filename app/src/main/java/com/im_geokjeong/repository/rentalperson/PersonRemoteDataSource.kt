package com.im_geokjeong.repository.rentalperson

import com.im_geokjeong.model.PersonResponse
import com.im_geokjeong.network.ApiClient

class PersonRemoteDataSource(private val apiClient: ApiClient):PersonDataSource {
    override suspend fun getArticle(): PersonResponse {
        return apiClient.getArticle()
    }
}