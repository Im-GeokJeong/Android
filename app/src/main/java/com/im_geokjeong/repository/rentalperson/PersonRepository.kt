package com.im_geokjeong.repository.rentalperson

import com.im_geokjeong.model.PersonResponse

class PersonRepository (private val remoteDataSource: PersonRemoteDataSource){
    suspend fun getArticle():PersonResponse{
        return remoteDataSource.getArticle()
    }
}