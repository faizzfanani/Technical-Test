package com.faizzfanani.service_pcs.domain.converter

import com.faizzfanani.core_storage.entity.PcsEntity
import com.faizzfanani.service_pcs.data.remote.response.PcsUserResponse
import com.faizzfanani.service_pcs.domain.model.PcsUser
import com.faizzfanani.service_pcs.utils.formatDate

fun PcsUserResponse.remoteToDomain() = PcsUser(
    createdAt = formatDate(createdAt),
    name = name,
    avatar = avatar,
    address = "St. $street, No. $addressNo, $city, $county $country, ($zipCode)",
    id = id
)

fun PcsUser.domainToEntity() = PcsEntity(
    createdAt = createdAt,
    name = name,
    avatar = avatar,
    address = address,
    id = id
)

fun PcsEntity.entityToDomain() = PcsUser(
    createdAt = createdAt,
    name = name,
    avatar = avatar,
    address = address,
    id = id
)