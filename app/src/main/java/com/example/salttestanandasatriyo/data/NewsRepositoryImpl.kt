package com.example.salttestanandasatriyo.data


import com.example.salttestanandasatriyo.common.NetworkAwareHandler
import com.example.salttestanandasatriyo.common.SharedPrefHelper
import com.example.salttestanandasatriyo.data.model.Article
import com.example.salttestanandasatriyo.data.sources.homeCahedData.OfflineDataSource
import com.example.salttestanandasatriyo.data.sources.remoteApi.OnlineDataSource
import com.example.salttestanandasatriyo.domain.NewsRepository
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val offlineDataSource: OfflineDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val sharedPreferences: SharedPrefHelper,
    private val networkHandler: NetworkAwareHandler
): NewsRepository {


    override suspend fun getNewsSources(isDataUpdated:Boolean): List<Article> {

        // you can change this logic depend on the business requirements
        return if (networkHandler.isOnline()) {

            if (sharedPreferences.runOnceADay()||isDataUpdated)
                 cacheArticles(getRemoteData())
            getCachedData()
        } else {
            getCachedData()
        }
    }



    override suspend fun updateFavorite(isFv: Int, url: String) = offlineDataSource.updateFav(isFv, url)

    private suspend fun cacheArticles(data: List<Article>) = offlineDataSource.cacheArticles(data)
    private fun getCachedData(): List<Article> = offlineDataSource.getArticles()
    private suspend fun getRemoteData(): List<Article> = onlineDataSource.getArticles()




}

