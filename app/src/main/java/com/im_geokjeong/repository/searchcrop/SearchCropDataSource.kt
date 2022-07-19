package com.im_geokjeong.repository.searchcrop

import com.im_geokjeong.model.CropResponse

interface SearchCropDataSource {
    suspend fun getMachineList(cropName: String): CropResponse
}