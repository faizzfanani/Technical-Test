package com.faizzfanani.service_github.domain.converter

import com.faizzfanani.core_storage.entity.GithubEntity
import com.faizzfanani.service_github.data.remote.response.GithubUserResponse
import com.faizzfanani.service_github.domain.model.GithubUser

fun GithubUserResponse.remoteToDomain() = GithubUser (
    username = login ?: "",
    id = id ?: 0,
    avatarUrl = avatarUrl ?: "",
    profileUrl = htmlUrl ?: "",
    followersCount = followers ?: 0,
    followingCount = following ?: 0,
    name = name ?: "",
    company = company ?: "",
    blog = blog ?: "",
    location = location ?: "",
    email = email ?: "",
    bio = bio ?: "",
    publicReposCount = publicRepos ?: 0,
    publicGistsCount = publicGists ?: 0,
    createdAt = createdAt ?: "",
    updatedAt = updatedAt ?: ""
)

fun GithubUser.domainToEntity() = GithubEntity (
    username = username,
    id = id,
    avatarUrl = avatarUrl,
    profileUrl = profileUrl,
    followersCount = followersCount,
    followingCount = followingCount,
    name = name,
    company = company,
    blog = blog,
    location = location,
    email = email,
    bio = bio,
    publicReposCount = publicReposCount,
    publicGistsCount = publicGistsCount,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun GithubEntity.entityToDomain() = GithubUser (
    username = username,
    id = id,
    avatarUrl = avatarUrl,
    profileUrl = profileUrl,
    followersCount = followersCount,
    followingCount = followingCount,
    name = name,
    company = company,
    blog = blog,
    location = location,
    email = email,
    bio = bio,
    publicReposCount = publicReposCount,
    publicGistsCount = publicGistsCount,
    createdAt = createdAt,
    updatedAt = updatedAt
)