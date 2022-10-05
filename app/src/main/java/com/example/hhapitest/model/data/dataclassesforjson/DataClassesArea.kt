package com.example.hhapitest.model.data.dataclassesforjson

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ShortArea(
    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("url") var url: String? = null
)
