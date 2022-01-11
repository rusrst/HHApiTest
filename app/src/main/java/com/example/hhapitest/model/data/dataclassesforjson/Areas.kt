package com.example.hhapitest.model.data.dataclassesforjson

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import java.io.Serializable


data class Area(@SerializedName("id") var id: String? = null,
                @SerializedName("parent_id") var parentId: String? = null,
                @SerializedName("name") var name: String? = null,
                @SerializedName("areas") var areas: List<Area>? = null): Serializable

@kotlinx.serialization.Serializable
data class AreaKS(@SerialName("id") var id: String? = null,
                  @SerialName("parent_id") var parentId: String? = null,
                  @SerialName("name") var name: String? = null,
                  @SerialName("areas") var areas: List<AreaKS>? = null)