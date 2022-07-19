package com.im_geokjeong.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.im_geokjeong.ServiceLocator
import com.im_geokjeong.network.ApiClient
import com.im_geokjeong.repository.modify.ModifyRemoteDataSource
import com.im_geokjeong.repository.modify.ModifyRepository
import com.im_geokjeong.repository.office.OfficeRemoteDataSource
import com.im_geokjeong.repository.office.OfficeRepository
import com.im_geokjeong.repository.officedetail.OfficeDetailRemoteDataSource
import com.im_geokjeong.repository.officedetail.OfficeDetailRepository
import com.im_geokjeong.repository.persondetail.PersonDetailRemoteDataSource
import com.im_geokjeong.repository.persondetail.PersonDetailRepository
import com.im_geokjeong.repository.rentalperson.ArticleRemoteDataSource
import com.im_geokjeong.repository.rentalperson.ArticleRepository
import com.im_geokjeong.repository.rentalpost.RentalPostRemoteDataSource
import com.im_geokjeong.repository.rentalpost.RentalPostRepository
import com.im_geokjeong.repository.searchcrop.SearchCropRemoteDataSource
import com.im_geokjeong.repository.searchcrop.SearchCropRepository
import com.im_geokjeong.ui.favorite.FavoriteViewModel
import com.im_geokjeong.ui.modfiy.ModifyViewModel
import com.im_geokjeong.ui.office.OfficeViewModel
import com.im_geokjeong.ui.officedetail.OfficeDetailViewModel
import com.im_geokjeong.ui.rentalperson.RentalPersonViewModel
import com.im_geokjeong.ui.rentalpersondetail.RentalPersonDetailViewModel
import com.im_geokjeong.ui.rentalpost.PostViewModel
import com.im_geokjeong.ui.searchcrop.SearchCropViewModel

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
                val articleRepository =
                    ArticleRepository(ArticleRemoteDataSource(ApiClient.create()))
                RentalPersonViewModel(articleRepository) as T
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
            modelClass.isAssignableFrom(RentalPersonDetailViewModel::class.java) -> {
                val repository =
                    PersonDetailRepository(PersonDetailRemoteDataSource(ApiClient.create()))
                return RentalPersonDetailViewModel(
                    repository,
                    ServiceLocator.provideFavoriteRepository(context)
                ) as T
            }
            modelClass.isAssignableFrom(ModifyViewModel::class.java) -> {
                val repository =
                    ModifyRepository(ModifyRemoteDataSource(ApiClient.create()))
                return ModifyViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(ServiceLocator.provideFavoriteRepository(context)) as T
            }
            modelClass.isAssignableFrom(SearchCropViewModel::class.java) -> {
                val repository =
                    SearchCropRepository(SearchCropRemoteDataSource(ApiClient.create()))
                return SearchCropViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}