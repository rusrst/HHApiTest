package com.example.hhapitest.model.database.dataclassroom

import androidx.room.*
import com.example.hhapitest.model.json.dataclassesforjson.*

@Entity(
    primaryKeys = [
        "id","publishAt"
    ],
    indices = [
        Index("createDB"),
        Index("requestID")
    ],
    foreignKeys = [
        ForeignKey(entity = RequestRoom::class,
                    parentColumns = ["id"],
                    childColumns = ["requestID"],
                    onUpdate = ForeignKey.CASCADE,
                    onDelete = ForeignKey.SET_NULL)
    ]
)
data class ResponseRoom (var id: Int = 0, var name: String? = null,
                         @Embedded(prefix = "department_")
                     var department: Department,
                         @Embedded(prefix = "area_")
                     var area: ShortArea,
                         @Embedded(prefix = "salary_")
                     var salary: ShortSalary,
                         @Embedded(prefix = "type_")
                     var type: TypeVacancy,
                         @Embedded(prefix = "address_")
                     var address: AddressRequest,
                         var publishAt: String = "",
                         var url: String = "", var altUrl: String = "",
                         @Embedded(prefix = "employer_")
                     var employer: Employer,
                         @Embedded(prefix = "snippet")
                     var snippet: Snippet,
                         var createDB: String = "",
                         var updateDB: String = "",
                         var requestID: Int? = null
)
data class Department(var id: String? = null, var name: String? = null)

data class ShortArea(
     var id: Int? = null,
     var name: String? = null,
     var url: String? = null){
}

data class ShortSalary(
    var from: Int? = null,
    var to: Int? = null,
    var currency: String? = null,
    var gross: Boolean? = null)

data class TypeVacancy(var id: String? = null, var name: String? = null)

data class AddressRequest(var city: String? = null,
                          var street: String? = null,
                          var building: String? = null,
                          var description: String? = null,
                          var lat: Double? = null,
                          var lng: Double? = null,
                          var raw: String? = null,
                          var metro: String? = null,
                          @Ignore var metro_stations: List<MetroStations?>? = null,
                          var id: Int? = null)

data class Employer(var id: Int? = null,
                    var name: String? = null,
                    var url: String? = null,
                    var alternate_url: String? = null,
                    @Ignore var logo_urls: LogoUrls? = null,
                    var vacancies_url: String? = null,
                    var trusted: Boolean? = null)

data class LogoUrls(var size_240: String? = null, var size_90: String? = null, var original: String? = null)

data class Snippet(var requirement: String? = null, var responsibility: String? = null)

fun ResponseRoom.myEqual(obj: ResponseRoom): Boolean{
    return (id == obj.id && publishAt == obj.publishAt)
}