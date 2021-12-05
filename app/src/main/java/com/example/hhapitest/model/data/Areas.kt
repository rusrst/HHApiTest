package com.example.hhapitest.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Area(@SerializedName("id") var id: String? = null,
                @SerializedName("parent_id") var parentId: String? = null,
                @SerializedName("name") var name: String? = null,
                @SerializedName("areas") var areas: List<Area>? = null)

@Entity
data class AreaRoom(
    @PrimaryKey var id: Int,
    var parentId: String?,
    var name: String?)