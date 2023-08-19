package com.faizfanani.movieapp.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
data class ReviewListResponse(
    @SerializedName("results")
    val reviewList: List<ReviewResponse>?
)

data class ReviewResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("author_details")
    val author: AuthorResponse?,
)

data class AuthorResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("rating")
    val rating: Int?,
)
