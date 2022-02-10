package com.jason.test.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jason.test.newsapp.data.NewsResult
import com.jason.test.newsapp.databinding.NewsItemBinding

class NewsResultAdapter :
    PagingDataAdapter<NewsResult, NewsResultAdapter.ViewHolder>(NewsResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsResult = getItem(position)
        holder.apply {
            newsResult?.let {
                bind(onCreateListener(it), it)
            }
        }
    }

    private fun onCreateListener(newsResult: NewsResult): View.OnClickListener {
        return View.OnClickListener {

        }
    }


    class ViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: NewsResult) {
            binding.apply {
                title.text = item.webTitle

            }
        }
    }
}