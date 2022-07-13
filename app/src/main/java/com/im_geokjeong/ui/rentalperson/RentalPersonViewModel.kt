package com.im_geokjeong.ui.rentalperson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Person
import com.im_geokjeong.repository.rentalperson.ArticleRepository
import com.im_geokjeong.ui.common.Event
import kotlinx.coroutines.launch

class RentalPersonViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

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
            val rentalPerson = articleRepository.getArticle()

            rentalPerson.let {
                _items.value = it.data
            }
        }
    }

    fun loadArticleData(title: String){
        viewModelScope.launch {
            val article = articleRepository.getArticleByTitle(title)

            article.let{
                _items.value = it.data
            }
        }
    }
}