package com.im_geokjeong.ui.searchcrop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.repository.searchcrop.SearchCropRepository
import com.im_geokjeong.ui.common.Event
import kotlinx.coroutines.launch

class SearchCropViewModel(private val machineRepository: SearchCropRepository) : ViewModel() {
    private val _machineList = MutableLiveData<List<String>>()
    val machineList: LiveData<List<String>> = _machineList

    private val _openOfficeListEvent = MutableLiveData<Event<String>>()
    val openOfficeListEvent: LiveData<Event<String>> = _openOfficeListEvent

    fun loadMachineList(cropName: String) {
        viewModelScope.launch {
            val machine = machineRepository.getMachine(cropName)
            machine.let {
                _machineList.value = it.data
            }
        }
    }

    fun openOfficeList(machine: String) {
        _openOfficeListEvent.value = Event(machine)
    }
}