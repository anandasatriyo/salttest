package com.example.salttestanandasatriyo.domain

import com.example.salttestanandasatriyo.data.model.Article
import javax.inject.Inject




class NewsUseCase @Inject constructor(private val repository: NewsRepository) {


     suspend fun getNewsUseCase(isDataUpdated:Boolean): List<Article> {
        return repository.getNewsSources(isDataUpdated)
    }



    suspend fun updateFavoriteUseCase(isFv:Int,url:String)=repository.updateFavorite(isFv,url)

}