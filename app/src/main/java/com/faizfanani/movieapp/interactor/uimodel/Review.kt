package com.faizfanani.movieapp.interactor.uimodel

/**
 * Created by Moh.Faiz Fanani on 19/08/2023
 */
data class Review(
    val id: String,
    val content: String,
    val createdAt: String,
    val author: Author?,
)

data class Author(
    val name: String,
    val username: String,
    val avatarPath: String,
    val rating: Int,
)
