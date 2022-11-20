package com.example.salttestanandasatriyo.data.sources.remoteApi


import android.util.Log
import com.example.salttestanandasatriyo.common.FIRST_SOURCE
import com.example.salttestanandasatriyo.common.SECOND_SOURCE
import com.example.salttestanandasatriyo.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface OnlineDataSource {
   suspend fun getArticles(): List<Article> = emptyList()
}


class OnlineDataSourceImpl @Inject constructor (private val service: ApiHelper) : OnlineDataSource {

   private val articlesNews = mutableListOf<Article>()

   companion object {
      var errorMsg: String = ""
   }



   @ExperimentalCoroutinesApi
   override suspend fun getArticles(): List<Article> {
      service.getArticles(FIRST_SOURCE).zip(service.getArticles(SECOND_SOURCE)) { firstSource, secondSource ->
            Log.e("TAG","calling api")
            return@zip mutableListOf<Article>().apply {
               firstSource.articles?.let {
                   Log.e("TAG",firstSource.articles?.toString())
                   addAll(it) }
               secondSource.articles?.let {
                   Log.e("TAG",secondSource.articles?.toString())
                   addAll(it) }
            }
         }.flowOn(Dispatchers.IO)
         .catch { e ->
            errorMsg = e.message.toString()
            Log.e("TAG", errorMsg)
         }
         .collect {
            articlesNews.addAll(it)
         }
      return articlesNews
   }








}
