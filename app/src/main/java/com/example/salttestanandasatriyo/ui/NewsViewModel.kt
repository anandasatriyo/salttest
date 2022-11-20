package com.example.salttestanandasatriyo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salttestanandasatriyo.common.Resource
import com.example.salttestanandasatriyo.data.model.Article
import com.example.salttestanandasatriyo.domain.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel   @Inject constructor (private val newsUseCase: NewsUseCase) : ViewModel() {

    private var articleNews = MutableLiveData<Resource<Article>>()
     var error = MutableLiveData<Boolean>()

     fun getHomeNews(isDataUpdated:Boolean) {
        articleNews.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            val result = newsUseCase.getNewsUseCase(isDataUpdated)

            articleNews.postValue(Resource.Success(result))
            if(result.isNullOrEmpty()){
                error.postValue(true)
            }
        }
    }

    fun getNews() = articleNews as LiveData<Resource<Article>>

}
