package com.example.hhapitest.model.data.database.dataclassroom

import androidx.room.*
import com.example.hhapitest.model.data.dataclassesforjson.MetroStations

@Entity(
    primaryKeys = [
        "id", "publishAt"
    ],
    indices = [
        Index("createDB"),
        Index("requestID")
    ],
    foreignKeys = [
        ForeignKey(
            entity = RequestRoom::class,
            parentColumns = ["id"],
            childColumns = ["requestID"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class ResponsesRoom(
    var id: String = "", var name: String = "",
    @Embedded(prefix = "department_")
    var department: Department? = null,
    @Embedded(prefix = "area_")
    var area: ShortArea,
    @Embedded(prefix = "salary_")
    var salary: ShortSalary? = null,
    @Embedded(prefix = "type_")
    var type: TypeVacancy?,
    @Embedded(prefix = "address_")
    var address: AddressRequest,
    var publishAt: String = "",
    var url: String = "", val altUrl: String = "",
    @Embedded(prefix = "employer_")
    var employer: Employer,
    @Embedded(prefix = "snippet")
    var snippet: Snippet,
    var createDB: String = "",
    var updateDB: String = "",
    var requestID: Int? = null
)

data class Department(val id: String?, val name: String?)

data class ShortArea(
    var id: Int? = null,
    var name: String? = null,
    var url: String? = null
) {
}

data class ShortSalary(
    val from: Int?,
    val to: Int?,
    val currency: String?,
    val gross: Boolean?
)

data class TypeVacancy(val id: String?, val name: String?)

data class AddressRequest(
    var city: String? = null,
    var street: String? = null,
    var building: String? = null,
    var description: String? = null,
    var lat: Double? = null,
    var lng: Double? = null,
    var raw: String? = null,
    var metro: String? = null,
    @Ignore var metro_stations: List<MetroStations?>? = null,
    var id: Int? = null
)

data class Employer(
    var id: Int? = null,
    var name: String? = null, var url: String? = null,
    var alternate_url: String? = null, @Ignore var logo_urls: LogoUrls? = null,
    var vacancies_url: String? = null, var trusted: Boolean? = null
)

data class LogoUrls(
    var size_240: String? = null,
    var size_90: String? = null,
    var original: String? = null
)

data class Snippet(val requirement: String? = null, val responsibility: String? = null)

fun ResponsesRoom.myEqual(obj: ResponsesRoom): Boolean {
    return (id == obj.id && publishAt == obj.publishAt)
}