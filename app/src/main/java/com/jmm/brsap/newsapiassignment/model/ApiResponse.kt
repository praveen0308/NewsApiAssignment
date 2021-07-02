package com.jmm.brsap.newsapiassignment.model

data class ApiResponse(
    val articles: List<Article>? = null,
    val status: String? = null,
    val totalResults: Int? = null
)