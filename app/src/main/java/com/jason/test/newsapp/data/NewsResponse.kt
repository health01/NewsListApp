package com.jason.test.newsapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_response")
data class NewsResponse(
    @SerializedName("currentPage")
    @ColumnInfo(defaultValue = "")
    val currentPage: Int,
    @ColumnInfo(defaultValue = "")
    @SerializedName("orderBy")
    val orderBy: String,
    @ColumnInfo(defaultValue = "")
    @SerializedName("pageSize")
    val pageSize: Int,
    @ColumnInfo(defaultValue = "")
    @SerializedName("pages")
    val pages: Int,
    @ColumnInfo(defaultValue = "")
    @SerializedName("startIndex")
    val startIndex: Int,
    @ColumnInfo(defaultValue = "")
    @SerializedName("status")
    val status: String,
    @ColumnInfo(defaultValue = "")
    @SerializedName("total")
    val total: Int,
    @ColumnInfo(defaultValue = "")
    @SerializedName("userTier")
    val userTier: String,
    @PrimaryKey
    @ColumnInfo(defaultValue = "")
    val keyWord: String
) {
    @Ignore
    lateinit var results: List<NewsResult>
}