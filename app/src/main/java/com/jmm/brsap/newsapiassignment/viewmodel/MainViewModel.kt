package com.jmm.brsap.newsapiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jmm.brsap.newsapiassignment.model.Article
import com.jmm.brsap.newsapiassignment.network.NewsApi
import com.jmm.brsap.newsapiassignment.repository.NewsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsApi: NewsApi
):ViewModel(){

    val articles: Flow<PagingData<Article>> =
        Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { NewsPagingSource(newsApi,"Tesla","2021-06-10","2021-07-02","publishedAt") }
        ).flow.cachedIn(viewModelScope)

}