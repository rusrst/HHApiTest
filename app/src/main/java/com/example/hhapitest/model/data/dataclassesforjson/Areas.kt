package com.example.hhapitest.model.data.dataclassesforjson

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class Area(@SerialName("id") var id: String? = null,
                @SerialName("parent_id") var parentId: String? = null,
                @SerialName("name") var name: String? = null,
                @SerialName("areas") var areas: List<Area>? = null)