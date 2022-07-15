package com.im_geokjeong.ui.rentalpersondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Favorite
import com.im_geokjeong.model.ModifyPerson
import com.im_geokjeong.model.Person
import com.im_geokjeong.model.PostResponse
import com.im_geokjeong.repository.favorite.FavoriteRepository
import com.im_geokjeong.repository.persondetail.PersonDetailRepository
import kotlinx.coroutines.launch

class RentalPersonDetailViewModel(private val personDetailRepository: PersonDetailRepository,private val favoriteRepository: FavoriteRepository) :
    ViewModel() {

    private val _item = MutableLiveData<Person>()
    val item: LiveData<Person> = _item
    private val _find = MutableLiveData<PostResponse>()
    val find: LiveData<PostResponse> = _find

    fun loadPersonDetail(postId: Int) {
        viewModelScope.launch {
            _item.value = personDetailRepository.getArticleDetail(postId).data
        }
    }

    fun deleteArticle(postId: Int) {
        viewModelScope.launch {
            personDetailRepository.deleteArticle(postId)
        }
    }

    fun getAuth(modify: ModifyPerson) {
        viewModelScope.launch {
            _find.value = personDetailRepository.getAuth(modify)
        }
    }

    fun addFavorite(favorite: Favorite)
    {
        viewModelScope.launch {
            favoriteRepository.addFavorite(favorite)
        }
    }

}
