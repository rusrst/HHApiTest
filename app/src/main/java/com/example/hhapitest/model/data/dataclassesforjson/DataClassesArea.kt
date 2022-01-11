package com.example.hhapitest.model.data.dataclassesforjson

import java.io.Serializable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ShortArea(
    @SerialName("id") var id: Int,
    @SerialName("name") var name: String,
    @SerialName("url") var url: String){
}
