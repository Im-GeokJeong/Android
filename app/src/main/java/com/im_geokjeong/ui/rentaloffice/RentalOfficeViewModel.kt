package com.im_geokjeong.ui.rentaloffice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Office
import com.im_geokjeong.model.OfficeResponse
import com.im_geokjeong.repository.OfficeRepository
import com.im_geokjeong.ui.common.Event
import kotlinx.coroutines.launch

class RentalOfficeViewModel(
    private val officeRepository: OfficeRepository
): ViewModel() {

    private val _items = MutableLiveData<OfficeResponse>()
    val items: LiveData<OfficeResponse> = _items

    private val _openOfficeEvent = MutableLiveData<Event<Office>>()
    val openOfficeEvent: LiveData<Event<Office>> = _openOfficeEvent

    init {
        loadOffice()
    }

    fun openOfficeDetail(office: Office){
        _openOfficeEvent.value = Event(office)
    }

    private fun loadOffice(){
        viewModelScope.launch {
            val offices = officeRepository.getOffices()
            _items.value = offices
        }
    }
}