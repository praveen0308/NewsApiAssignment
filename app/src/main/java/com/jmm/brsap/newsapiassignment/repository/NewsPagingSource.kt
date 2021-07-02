package com.jmm.brsap.newsapiassignment.repository

import androidx.paging.PagingSource
import com.jmm.brsap.newsapiassignment.model.Article
import com.jmm.brsap.newsapiassignment.network.NewsApi
import com.jmm.brsap.newsapiassignment.util.Constants
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 1
class NewsPagingSource(
    private val apiService: NewsApi,
    private val query:String,
    private val from:String,
    private val to:String,
    private val sortBy:String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {

            val response = apiService.getEverything(query,from,to,sortBy,Constants.API_KEY,params.loadSize,position)
            val articles = response.articles
            LoadResult.Page(
                data = articles!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {

            LoadResult.Error(exception)
        }
    }
}