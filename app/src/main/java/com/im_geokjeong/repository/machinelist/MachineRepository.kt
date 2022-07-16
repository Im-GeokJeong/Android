package com.im_geokjeong.repository.machinelist

import com.im_geokjeong.model.CropResponse

class MachineRepository(private val remoteDataSource: MachineDataSource) {

    suspend fun getMachine(cropName: String): CropResponse {
        return remoteDataSource.getMachineList(cropName)
    }
}