package com.im_geokjeong.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.im_geokjeong.network.ApiClient
import com.im_geokjeong.repository.rentalperson.PersonRemoteDataSource
import com.im_geokjeong.repository.rentalperson.PersonRepository
import com.im_geokjeong.repository.rentalpost.RentalPostRemoteDataSource
import com.im_geokjeong.repository.rentalpost.RentalPostRepository
import com.im_geokjeong.ui.rentalperson.RentalPersonFragment
import com.im_geokjeong.ui.rentalperson.RentalPersonViewModel
import com.im_geokjeong.ui.rentalpost.PostViewModel
import com.im_geokjeong.repository.office.OfficeRemoteDataSource
import com.im_geokjeong.repository.office.OfficeRepository
import com.im_geokjeong.repository.officedetail.OfficeDetailRemoteDataSource
import com.im_geokjeong.repository.officedetail.OfficeDetailRepository
import com.im_geokjeong.ui.office.OfficeViewModel
import com.im_geokjeong.ui.officedetail.OfficeDetailViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(OfficeViewModel::class.java) -> {
                val repository = OfficeRepository(OfficeRemoteDataSource(ApiClient.create()))
                OfficeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PostViewModel::class.java) -> {
                val repository =
                    RentalPostRepository(RentalPostRemoteDataSource(ApiClient.create()))
                PostViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RentalPersonViewModel::class.java) -> {
                val repository = PersonRepository(PersonRemoteDataSource(ApiClient.create()))
                RentalPersonViewModel(repository) as T
            }
            modelClass.isAssignableFrom(OfficeViewModel::class.java) -> {
                val repository = OfficeRepository(OfficeRemoteDataSource(ApiClient.create()))
                return OfficeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(OfficeDetailViewModel::class.java) -> {
                val repository =
                    OfficeDetailRepository(OfficeDetailRemoteDataSource(ApiClient.create()))
                return OfficeDetailViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}