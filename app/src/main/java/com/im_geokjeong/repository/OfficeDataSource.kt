package com.im_geokjeong.repository

import com.im_geokjeong.model.OfficeResponse

interface OfficeDataSource {

    suspend fun getOffices(): OfficeResponse
}