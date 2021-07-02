package com.jmm.brsap.newsapiassignment.repository

import com.jmm.brsap.newsapiassignment.network.NewsApi
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val newsApi: NewsApi
){


}