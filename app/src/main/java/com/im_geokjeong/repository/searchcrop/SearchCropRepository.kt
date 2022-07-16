package com.im_geokjeong.repository.searchcrop

import com.im_geokjeong.model.CropResponse
import com.im_geokjeong.model.OfficeRequest
import com.im_geokjeong.model.OfficeResponse

class SearchCropRepository(private val remoteDataSource: SearchCropDataSource) {

    suspend fun getMachine(cropName: String): CropResponse {
        return remoteDataSource.getMachineList(cropName)
    }

    suspend fun getOfficeList(officeRequest: OfficeRequest): OfficeResponse{
        return remoteDataSource.postMachine(officeRequest)
    }
}