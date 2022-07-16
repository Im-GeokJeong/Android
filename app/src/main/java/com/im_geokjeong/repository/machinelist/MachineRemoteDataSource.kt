package com.im_geokjeong.repository.machinelist

import com.im_geokjeong.model.CropResponse
import com.im_geokjeong.network.ApiClient

class MachineRemoteDataSource(private val api: ApiClient): MachineDataSource {
    override suspend fun getMachineList(cropName: String): CropResponse {
        return api.getMachineList(cropName)
    }
}