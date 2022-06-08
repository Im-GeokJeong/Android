package com.im_geokjeong.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.im_geokjeong.network.ApiClient
import com.im_geokjeong.repository.OfficeRemoteDataSource
import com.im_geokjeong.repository.OfficeRepository
import com.im_geokjeong.ui.rentaloffice.RentalOfficeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RentalOfficeViewModel::class.java)){
            val repository = OfficeRepository(OfficeRemoteDataSource(ApiClient.create()))
            return RentalOfficeViewModel(repository) as T
        }else{
            throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
        }
    }

}