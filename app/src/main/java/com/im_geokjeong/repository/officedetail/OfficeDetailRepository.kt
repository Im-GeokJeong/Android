package com.im_geokjeong.repository.officedetail

import com.im_geokjeong.model.OfficeDetailResponse

class OfficeDetailRepository(private val remoteDataSource: OfficeDetailDataSource) {

    suspend fun getOfficeDetail(officeId: Long): OfficeDetailResponse{
        return remoteDataSource.getOfficeDetail(officeId)
    }
}