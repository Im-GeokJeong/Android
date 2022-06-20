package com.im_geokjeong.ui.rentalperson

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Person
import com.im_geokjeong.model.PersonResponse
import com.im_geokjeong.repository.rentalperson.PersonRepository
import kotlinx.coroutines.launch

class RentalPersonViewModel (private val personRepository: PersonRepository): ViewModel() {

    private val _items = MutableLiveData<List<Person>>()
    val items: LiveData<List<Person>> = _items

    init {
        loadRentalData()
    }
    private fun loadRentalData() {
        viewModelScope.launch {
            val rentalPerson = personRepository.getArticle()

            rentalPerson.let{
                Log.e("rentalPerson",it.data.toString())
                _items.value=it.data
            }
        }
    }
}