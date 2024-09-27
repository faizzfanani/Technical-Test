package com.faizzfanani.service_github.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(
    @SerializedName("login")
    val login: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("following")
    val following: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("blog")
    val blog: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("public_repos")
    val publicRepos: Int?,
    @SerializedName("public_gists")
    val publicGists: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)