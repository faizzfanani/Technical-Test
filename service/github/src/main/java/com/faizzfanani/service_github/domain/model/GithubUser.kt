package com.faizzfanani.service_github.domain.model

data class GithubUser(
    val username: String,
    val id: Int,
    val avatarUrl: String,
    val profileUrl: String,
    val followersCount: Int,
    val followingCount: Int,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String,
    val publicReposCount: Int,
    val publicGistsCount: Int,
    val createdAt: String,
    val updatedAt: String,
)