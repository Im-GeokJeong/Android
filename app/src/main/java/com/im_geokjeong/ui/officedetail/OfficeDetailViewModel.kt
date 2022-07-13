package com.im_geokjeong.ui.officedetail

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.OfficeDetailResponse
import com.im_geokjeong.repository.officedetail.OfficeDetailRepository
import kotlinx.coroutines.launch

class OfficeDetailViewModel(private val officeDetailRepository: OfficeDetailRepository): ViewModel() {

    private val _office = MutableLiveData<OfficeDetailResponse>()
    val office: LiveData<OfficeDetailResponse> = _office

    fun loadOfficeDetail(officeId: Int){
        viewModelScope.launch {
            val officeDetail = officeDetailRepository.getOfficeDetail(officeId)
            _office.value = officeDetail
        }
    }
}