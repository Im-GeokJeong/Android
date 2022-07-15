package com.im_geokjeong.ui.favorite


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Favorite
import com.im_geokjeong.model.Person
import com.im_geokjeong.repository.favorite.FavoriteRepository
import com.im_geokjeong.ui.common.Event
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    private val _items = MutableLiveData<List<Favorite>>()
    val items: LiveData<List<Favorite>> = _items

    private val _openPersonEvent = MutableLiveData<Event<Favorite>>()
    val openPersonEvent: LiveData<Event<Favorite>> = _openPersonEvent

    init {
        loadFavorite()
    }
    private fun loadFavorite(){
        viewModelScope.launch {
            _items.value=favoriteRepository.getFavorite()
        }
    }
    fun openFavoriteDetail(favorite: Favorite) {
        _openPersonEvent.value = Event(favorite)
    }
}