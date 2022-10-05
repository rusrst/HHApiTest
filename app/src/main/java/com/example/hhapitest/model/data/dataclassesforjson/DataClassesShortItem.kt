package com.example.hhapitest.model.data.dataclassesforjson

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ListRequest(
    var items: List<ShortItem>?,
    var found: Int?,
    var pages: Int?,
    var per_page: Int?,
    var page: Int?,
    var clusters: String?,
    var arguments: String?,
    val alternate_url: String?
)

@kotlinx.serialization.Serializable
data class ShortItem(
    val id: Int?,
    val premium: Boolean?,
    val name: String?,
    val department: Department?,
    val has_test: Boolean?,
    val response_letter_required: Boolean?,
    val area: ShortArea?,
    val salary: ShortSalary?,
    val type: TypeVacancy?,
    val address: AddressRequest?,
    val response_url: String?,
    val sort_point_distance: String?,
    val published_at: String?,
    val created_at: String?,
    val archived: Boolean?,
    val apply_alternate_url: String?,
    val insider_interview: InsiderInterview?,
    val url: String?,
    val alternate_url: String?,
    val relations: List<String?>?,// при авторизации может появиться
    val employer: Employer?,
    val snippet: Snippet?,
    val contacts: String?,
    val schedule: Schedule?,
    val working_days: List<WorkingDays?>?,
    val working_time_intervals: List<WorkingTimeIntervals?>?,
    val working_time_modes: List<WorkingTimeModes?>?,
    val accept_temporary: Boolean?,
    @SerialName("immediate_redirect_url") val immediate_redirect_url: String? = null
)

@kotlinx.serialization.Serializable
data class Department(val id: String? = null, val name: String? = null)

@kotlinx.serialization.Serializable
data class TypeVacancy(val id: String?, val name: String?)

@kotlinx.serialization.Serializable
data class AddressRequest(
    var city: String? = null,
    var street: String? = null,
    var building: String? = null,
    var description: String? = null,
    var lat: Double? = null,
    var lng: Double? = null,
    var raw: String? = null,
    var metro: String? = null,
    val metro_stations: List<MetroStations?>?,
    var id: Int? = null
)

@kotlinx.serialization.Serializable
data class ShortSalary(
    val from: Int?,
    val to: Int?,
    val currency: String?,
    val gross: Boolean?
)

@kotlinx.serialization.Serializable
data class Employer(
    val id: Int? = null,
    val name: String? = null,
    val url: String? = null,
    val alternate_url: String? = null,
    val logo_urls: LogoUrls?,
    val vacancies_url: String? = null,
    val trusted: Boolean? = null
)

@kotlinx.serialization.Serializable
data class LogoUrls(
    @SerialName("240") val size_240: String? = null,
    @SerialName("90") val size_90: String? = null,
    @SerialName("original") val original: String? = null
)

@kotlinx.serialization.Serializable
data class Snippet(val requirement: String? = null, val responsibility: String? = null)

@kotlinx.serialization.Serializable
data class Schedule(val id: String?, val name: String?)

@kotlinx.serialization.Serializable
data class MetroStations(
    val station_id: String? = null,
    val station_name: String? = null,
    val line_id: String? = null,
    val line_name: String? = null,
    val lat: Long? = null,
    val lng: Long? = null
)

@kotlinx.serialization.Serializable
data class WorkingDays(val id: String?, val name: String?)

@kotlinx.serialization.Serializable
data class WorkingTimeIntervals(val id: String?, val name: String?)

@kotlinx.serialization.Serializable
data class WorkingTimeModes(val id: String?, val name: String?)

@kotlinx.serialization.Serializable
data class InsiderInterview(val id: String?, val url: String?)