package com.example.hhapitest.model.data

import com.google.gson.annotations.SerializedName

data class Areas(var areas: List<Area>)

data class Area(@SerializedName("id") var id: String? = null,
                @SerializedName("parent_id") var parentId: String? = null,
                @SerializedName("name") var name: String? = null,
                @SerializedName("areas") var areas: List<Area>? = null)
