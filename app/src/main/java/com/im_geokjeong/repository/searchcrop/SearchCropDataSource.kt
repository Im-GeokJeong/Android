package com.im_geokjeong.repository.searchcrop

import com.im_geokjeong.model.CropResponse
import com.im_geokjeong.model.OfficeRequest
import com.im_geokjeong.model.OfficeResponse

interface SearchCropDataSource {
    suspend fun getMachineList(cropName: String): CropResponse
    suspend fun postMachine(officeRequest: OfficeRequest): OfficeResponse
}