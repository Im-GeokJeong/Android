package com.im_geokjeong.repository.machinelist

import com.im_geokjeong.model.CropResponse

interface MachineDataSource {
    suspend fun getMachineList(cropName: String): CropResponse
}