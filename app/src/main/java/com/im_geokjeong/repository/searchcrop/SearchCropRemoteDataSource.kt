package com.im_geokjeong.repository.searchcrop

import com.im_geokjeong.model.CropResponse
import com.im_geokjeong.network.ApiClient

class SearchCropRemoteDataSource(private val apiClient: ApiClient) : SearchCropDataSource {
    override suspend fun getMachineList(cropName: String): CropResponse {
        return apiClient.getMachineList(cropName)
    }
}