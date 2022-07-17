package com.im_geokjeong.ui.searchcrop

import androidx.lifecycle.*
import com.im_geokjeong.model.Office
import com.im_geokjeong.model.OfficeRequest
import com.im_geokjeong.repository.searchcrop.SearchCropRepository
import com.im_geokjeong.ui.common.Event
import kotlinx.coroutines.launch

class MachineListViewModel(private val machineRepository: SearchCropRepository) : ViewModel() {
    private val _machineList = MutableLiveData<List<String>>()
    val machineList: LiveData<List<String>> = _machineList

    private val _officeList = MutableLiveData<List<Office>>()
    val officeList: LiveData<List<Office>> = _officeList

    private val _openOfficeListEvent = MutableLiveData<Event<String>>()
    val openOfficeListEvent : LiveData<Event<String>> = _openOfficeListEvent

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

    fun loadOfficeList(machine: String, latitude: String, longitude: String){
        viewModelScope.launch {
            val officeRequest = OfficeRequest(machine, latitude, longitude)
            val officeList = machineRepository.getOfficeList(officeRequest)
            _officeList.value = officeList.data
        }
    }
}