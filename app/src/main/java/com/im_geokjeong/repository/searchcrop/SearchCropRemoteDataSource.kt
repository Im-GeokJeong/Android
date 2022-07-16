package com.im_geokjeong.repository.searchcrop

import com.im_geokjeong.model.CropResponse
import com.im_geokjeong.model.OfficeRequest
import com.im_geokjeong.model.OfficeResponse
import com.im_geokjeong.network.ApiClient

class SearchCropRemoteDataSource(private val api: ApiClient): SearchCropDataSource {
    override suspend fun getMachineList(cropName: String): CropResponse {
        return api.getMachineList(cropName)
    }

    override suspend fun postMachine(officeRequest: OfficeRequest): OfficeResponse {
        return api.postMachine(officeRequest)
    }
}