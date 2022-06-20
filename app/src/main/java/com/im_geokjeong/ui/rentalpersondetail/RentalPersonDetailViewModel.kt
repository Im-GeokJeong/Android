package com.im_geokjeong.ui.rentalpersondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Person
import com.im_geokjeong.repository.persondetail.PersonDetailRepository
import kotlinx.coroutines.launch

class RentalPersonDetailViewModel(private val personDetailRepository: PersonDetailRepository) :
    ViewModel() {

    private val _item = MutableLiveData<Person>()
    val item: LiveData<Person> = _item

    fun loadPersonDetail(postId: Int) {
        viewModelScope.launch {
            _item.value = personDetailRepository.getArticleDetail(postId).data
        }
    }
}
