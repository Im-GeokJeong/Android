package com.im_geokjeong.ui.searchcrop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Office
import com.im_geokjeong.model.OfficeRequest
import com.im_geokjeong.repository.searchcrop.SearchCropRepository
import com.im_geokjeong.ui.common.Event
import kotlinx.coroutines.launch

class MachineListViewModel(private val machineRepository: SearchCropRepository): ViewModel() {
    private val _machineList = MutableLiveData<List<String>>()
    val machineList: LiveData<List<String>> = _machineList

    private val _officeList = MutableLiveData<List<Office>>()
    val officeList : LiveData<List<Office>> = _officeList

    fun loadMachineList(cropName: String){
        viewModelScope.launch {
            val machine = machineRepository.getMachine(cropName)
            machine.let {
                _machineList.value = it.data
            }
        }
    }

    fun openOfficeList(machine: String, latitude: String, longitude: String){
        viewModelScope.launch {
            val officeRequest = OfficeRequest(machine, latitude, longitude)
            val officeList = machineRepository.getOfficeList(officeRequest)
            _officeList.value = officeList.data
        }
    }
}