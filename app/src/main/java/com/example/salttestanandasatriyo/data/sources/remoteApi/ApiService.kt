package com.example.salttestanandasatriyo.data.sources.remoteApi

import com.example.salttestanandasatriyo.common.COUNTRY
import com.example.salttestanandasatriyo.common.END_POINT
import com.example.salttestanandasatriyo.common.HEADLINE
import com.example.salttestanandasatriyo.common.SOURCE
import com.example.salttestanandasatriyo.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(HEADLINE)
    suspend fun getArticlesNews(@Query(COUNTRY) sourceName: String): NewsResponse

}

interface HeadlineApiService {
    @GET(HEADLINE)
    suspend fun getHeadlineArticles(@Query(COUNTRY) sourceName: String): NewsResponse
}