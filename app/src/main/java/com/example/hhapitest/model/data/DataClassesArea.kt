package com.example.hhapitest.model.data

import com.google.gson.annotations.SerializedName

data class ListShortArea( var items: List<ShortArea>)
data class ShortArea(
    @SerializedName("id") var id: Int,
    @SerializedName("text") var name: String,
    @SerializedName("url") var url: String){
}
