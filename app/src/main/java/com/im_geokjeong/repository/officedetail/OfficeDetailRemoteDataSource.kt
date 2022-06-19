package com.im_geokjeong.repository.officedetail

import com.im_geokjeong.model.OfficeDetailResponse
import com.im_geokjeong.network.ApiClient

class OfficeDetailRemoteDataSource(private val api: ApiClient): OfficeDetailDataSource {
    override suspend fun getOfficeDetail(officeId: Int): OfficeDetailResponse {
        return api.getOfficeDetail(officeId)
    }

}