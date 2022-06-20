package com.im_geokjeong.ui.rentalpost

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.im_geokjeong.model.PostPerson
import com.im_geokjeong.model.PostResponse
import com.im_geokjeong.repository.rentalpost.RentalPostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val postRepository: RentalPostRepository) : ViewModel() {

    private val _items = MutableLiveData<PostResponse>()
    val items: LiveData<PostResponse> = _items

    fun uploadPost(person: PostPerson) {
        viewModelScope.launch {
            val response = postRepository.postPerson(person)

            Log.e("response", response.toString())//예외상황 설정
        }
    }

}