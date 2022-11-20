package com.example.salttestanandasatriyo.domain

import com.example.salttestanandasatriyo.data.model.Article

interface NewsRepository {

    suspend fun getNewsSources(isDataUpdated:Boolean): List<Article>

    suspend fun updateFavorite(isFv: Int, url: String)
}