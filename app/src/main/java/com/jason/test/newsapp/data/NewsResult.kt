package com.jason.test.newsapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_result", primaryKeys = ["id"])
data class NewsResult(
    @SerializedName("apiUrl")
    @ColumnInfo(defaultValue = "")
    val apiUrl: String,
    @SerializedName("id")
    @ColumnInfo(defaultValue = "")
    val id: String,
    @SerializedName("isHosted")
    @ColumnInfo(defaultValue = "")
    val isHosted: Boolean,
    @SerializedName("pillarId")
    @ColumnInfo(defaultValue = "")
    val pillarId: String,
    @SerializedName("pillarName")
    @ColumnInfo(defaultValue = "")
    val pillarName: String,
    @SerializedName("sectionId")
    @ColumnInfo(defaultValue = "")
    val sectionId: String,
    @SerializedName("sectionName")
    @ColumnInfo(defaultValue = "")
    val sectionName: String,
    @SerializedName("type")
    @ColumnInfo(defaultValue = "")
    val type: String,
    @SerializedName("webPublicationDate")
    @ColumnInfo(defaultValue = "")
    val webPublicationDate: String,
    @SerializedName("webTitle")
    @ColumnInfo(defaultValue = "")
    val webTitle: String,
    @SerializedName("webUrl")
    @ColumnInfo(defaultValue = "2")
    val webUrl: String
)