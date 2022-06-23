package com.im_geokjeong.ui.modfiy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.Person
import com.im_geokjeong.repository.modify.ModifyRepository
import kotlinx.coroutines.launch

class ModifyViewModel(private val postRepository: ModifyRepository) : ViewModel() {

    private val _items = MutableLiveData<Person>()
    val items: LiveData<Person> = _items

    fun loadPersonDetail(postId: Int) {
        viewModelScope.launch {
            _items.value = postRepository.getArticleDetail(postId).data
        }
    }

    fun updateArticle(person: Person) {
        viewModelScope.launch {
            val response = postRepository.updateArticle(person)

            Log.e("response", response.toString())//예외상황 설정
        }
    }
}