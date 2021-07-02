package com.jmm.brsap.newsapiassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.newsapiassignment.R
import com.jmm.brsap.newsapiassignment.adapter.MyLoadStateAdapter
import com.jmm.brsap.newsapiassignment.adapter.NewsPagingAdapter
import com.jmm.brsap.newsapiassignment.databinding.ActivityMainBinding
import com.jmm.brsap.newsapiassignment.model.Article
import com.jmm.brsap.newsapiassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NewsPagingAdapter.NewsAdapterInterface {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsPagingAdapter:NewsPagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRvData()

        lifecycleScope.launch {
            viewModel.articles.collectLatest { pagedData ->
                newsPagingAdapter.submitData(pagedData)
            }
        }

    }

    private fun setupRvData() {
        newsPagingAdapter = NewsPagingAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)

            this.layoutManager = layoutManager
            adapter = newsPagingAdapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter { newsPagingAdapter.retry() },
                footer = MyLoadStateAdapter { newsPagingAdapter.retry() }
            )
        }
    }

    override fun onItemClick(item: Article) {
        val intent = Intent(this, WebActivity::class.java).apply {
            putExtra("URL", item.url)
        }
        startActivity(intent)
    }
}