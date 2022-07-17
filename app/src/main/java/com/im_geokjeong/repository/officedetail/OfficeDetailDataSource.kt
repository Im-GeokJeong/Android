package com.im_geokjeong.repository.officedetail

import com.im_geokjeong.model.OfficeDetailResponse

interface OfficeDetailDataSource {

    suspend fun getOfficeDetail(officeId: Long): OfficeDetailResponse
}