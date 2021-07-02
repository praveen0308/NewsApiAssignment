package com.jmm.brsap.newsapiassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.newsapiassignment.databinding.TemplateNewsItemBinding
import com.jmm.brsap.newsapiassignment.model.Article
import com.jmm.brsap.newsapiassignment.util.setImageWithGlide

class NewsPagingAdapter(private val mListener: NewsAdapterInterface) :
    PagingDataAdapter<Article, NewsPagingAdapter.ArticleViewHolder>(NewsComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        return ArticleViewHolder(
            TemplateNewsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),mListener
        )
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindArticle(it) }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class ArticleViewHolder(
        private val binding: TemplateNewsItemBinding,
        private val mListener: NewsAdapterInterface,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {

           itemView.setOnClickListener {
               getItem(absoluteAdapterPosition)?.let { it1 -> mListener.onItemClick(it1) }
           }
        }
        fun bindArticle(item: Article)  {
            binding.apply{
                item.urlToImage?.let { imageView.setImageWithGlide(it) }
                tvTitle.text = item.title
                tvDescription.text = item.description
            }

        }
    }

    interface  NewsAdapterInterface{
        fun onItemClick(item: Article)
    }

    companion object {
        private val NewsComparator = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}