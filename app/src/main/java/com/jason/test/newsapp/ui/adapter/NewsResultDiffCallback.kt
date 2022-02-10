package com.jason.test.newsapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jason.test.newsapp.data.NewsResult

class NewsResultDiffCallback : DiffUtil.ItemCallback<NewsResult>() {
    override fun areItemsTheSame(oldItem: NewsResult, newItem: NewsResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsResult, newItem: NewsResult): Boolean {
        return oldItem == newItem
    }
}