package com.im_geokjeong.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.im_geokjeong.network.ApiClient
import com.im_geokjeong.repository.OfficeRemoteDataSource
import com.im_geokjeong.repository.OfficeRepository
import com.im_geokjeong.repository.rentalperson.PersonRemoteDataSource
import com.im_geokjeong.repository.rentalperson.PersonRepository
import com.im_geokjeong.repository.rentalpost.RentalPostRemoteDataSource
import com.im_geokjeong.repository.rentalpost.RentalPostRepository
import com.im_geokjeong.ui.rentaloffice.RentalOfficeViewModel
import com.im_geokjeong.ui.rentalperson.RentalPersonFragment
import com.im_geokjeong.ui.rentalperson.RentalPersonViewModel
import com.im_geokjeong.ui.rentalpost.PostViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RentalOfficeViewModel::class.java) -> {
                val repository = OfficeRepository(OfficeRemoteDataSource(ApiClient.create()))
                RentalOfficeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PostViewModel::class.java) -> {
                val repository=RentalPostRepository(RentalPostRemoteDataSource(ApiClient.create()))
                PostViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RentalPersonViewModel::class.java) -> {
                val repository=PersonRepository(PersonRemoteDataSource(ApiClient.create()))
                RentalPersonViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}