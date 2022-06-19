package com.im_geokjeong.repository.office

import com.im_geokjeong.model.OfficeResponse

class OfficeRepository(
    private val remoteDataSource: OfficeRemoteDataSource
) {
    suspend fun getOffices(): OfficeResponse {
        return remoteDataSource.getOffices()
    }
}