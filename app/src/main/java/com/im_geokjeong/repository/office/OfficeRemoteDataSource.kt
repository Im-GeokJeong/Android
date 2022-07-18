package com.im_geokjeong.repository.office

import com.im_geokjeong.model.OfficeRequest
import com.im_geokjeong.model.OfficeResponse
import com.im_geokjeong.network.ApiClient

class OfficeRemoteDataSource(private val apiClient: ApiClient) : OfficeDataSource {

    override suspend fun getOffices(): OfficeResponse {
        return apiClient.getOffices()
    }

    override suspend fun postMachine(officeRequest: OfficeRequest): OfficeResponse {
        return apiClient.postMachine(officeRequest)
    }
}