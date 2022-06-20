package com.im_geokjeong.ui.rentalperson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Person
import com.im_geokjeong.repository.rentalperson.PersonRepository
import com.im_geokjeong.ui.common.Event
import kotlinx.coroutines.launch

class RentalPersonViewModel(private val personRepository: PersonRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Person>>()
    val items: LiveData<List<Person>> = _items

    private val _openPersonEvent = MutableLiveData<Event<Person>>()
    val openPersonEvent: LiveData<Event<Person>> = _openPersonEvent

    init {
        loadRentalData()
    }

    fun openPersonDetail(person: Person) {
        _openPersonEvent.value = Event(person)
    }

    private fun loadRentalData() {
        viewModelScope.launch {
            val rentalPerson = personRepository.getArticle()

            rentalPerson.let {
                _items.value = it.data
            }
        }
    }
}