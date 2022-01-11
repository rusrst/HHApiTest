package com.example.hhapitest.model.json

import android.util.Log
import com.example.hhapitest.model.data.dataclassesforjson.Area
import com.example.hhapitest.model.data.dataclassesforjson.AreaKS
import com.example.hhapitest.model.data.dataclassesforjson.EmployerRequest
import com.example.hhapitest.model.data.dataclassesforjson.EmployersRequest
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray

typealias GetListAreas = (String) -> List<Area>

class Json {
companion object {
    fun getListAreas(body: String): List<Area> {
        val data = Json.decodeFromString<List<AreaKS>>(body)
        Log.d("TAG","${data[2]}")
        val gson = Gson()
        return getListAreasWithNullAreas(
            gson.fromJson(body, Array<Area>::class.java).toList(),
            mutableListOf()
        )
    }


    private fun getListAreasWithNullAreas(
        list: List<Area>,
        newList: MutableList<Area>
    ): MutableList<Area> {
        list.forEach { area ->
            var newArea = Area().apply {
                id = area.id
                parentId = area.parentId
                name = area.name
            }
            newArea.areas = null
            newList.add(newArea)
            if (area.areas != null) {
                getListAreasWithNullAreas(area.areas!!, newList)
            }
        }
        return newList
    }

    fun getListEmployersForRequestList(body: String): List<EmployerRequest>?{
        val gson = Gson()
        val data = gson.fromJson(body, EmployersRequest::class.java)
        return data.items
    }
}

}