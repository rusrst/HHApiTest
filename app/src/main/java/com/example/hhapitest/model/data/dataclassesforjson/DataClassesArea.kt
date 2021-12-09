package com.example.hhapitest.model.data.dataclassesforjson

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class ShortArea(
    @SerializedName("id") var id: Int,
    @SerializedName("text") var name: String,
    @SerializedName("url") var url: String): Serializable{
}
