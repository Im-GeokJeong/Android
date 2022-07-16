package com.im_geokjeong.ui.searchcrop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.repository.machinelist.MachineRepository
import kotlinx.coroutines.launch

class MachineListViewModel(private val machineRepository: MachineRepository): ViewModel() {
    private val _machineList = MutableLiveData<List<String>>()
    val machineList: LiveData<List<String>> = _machineList

    fun loadMachineList(cropName: String){
        viewModelScope.launch {
            val machine = machineRepository.getMachine(cropName)
            machine.let {
                _machineList.value = it.data
            }
        }
    }
}