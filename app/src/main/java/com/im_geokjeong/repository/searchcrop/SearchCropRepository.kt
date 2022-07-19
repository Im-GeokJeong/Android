package com.im_geokjeong.repository.searchcrop

import com.im_geokjeong.model.CropResponse

class SearchCropRepository(private val remoteDataSource: SearchCropDataSource) {

    suspend fun getMachine(cropName: String): CropResponse {
        return remoteDataSource.getMachineList(cropName)
    }
}