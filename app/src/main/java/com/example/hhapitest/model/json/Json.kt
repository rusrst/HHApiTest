package com.example.hhapitest.model.json

import com.example.hhapitest.model.data.dataclassesforjson.Area
import com.example.hhapitest.model.data.dataclassesforjson.EmployerRequest
import com.example.hhapitest.model.data.dataclassesforjson.EmployersRequest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

typealias GetListAreas = (String) -> List<Area>

class Json {
companion object {
    fun getListAreas(body: String): List<Area> {
        val data = Json.decodeFromString<List<Area>>(body)
        return getListAreasWithNullAreas(
            data,
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
        val data = Json.decodeFromString<EmployersRequest?>(body)
        return data?.items
    }
}

}