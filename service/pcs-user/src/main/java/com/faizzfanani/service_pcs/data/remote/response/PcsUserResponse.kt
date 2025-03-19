package com.faizzfanani.service_pcs.data.remote.response

import com.google.gson.annotations.SerializedName

data class PcsUserResponse(
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String,
    @SerializedName("county") val county: String,
    @SerializedName("address_no") val addressNo: String,
    @SerializedName("street") val street: String,
    @SerializedName("zip_code") val zipCode: String,
    @SerializedName("id") val id: String
)