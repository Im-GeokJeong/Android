package com.im_geokjeong.repository.office

import com.im_geokjeong.model.OfficeRequest
import com.im_geokjeong.model.OfficeResponse

interface OfficeDataSource {

    suspend fun getOffices(): OfficeResponse
    suspend fun postMachine(officeRequest: OfficeRequest): OfficeResponse
}