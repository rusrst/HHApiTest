package com.example.hhapitest.model.data.dataclassesforjson

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ListRequest (var items: List<ShortItem>?,
                        var found: Int?,
                        var pages: Int?,
                        var per_page: Int?,
                        var page: Int?,
                        var clusters: String?,
                        var arguments: String?,
                        val alternate_url: String?)
@kotlinx.serialization.Serializable
data class ShortItem (val id: Int?,
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
                      val relations: List<NNUL?>?,
                      val employer: Employer?,
                      val snippet: Snippet?,
                      val contacts: String?,
                      val schedule: Schedule?,
                      val working_days: List<WorkingDays?>?,
                      val working_time_intervals: List<WorkingTimeIntervals?>?,
                      val working_time_modes: List<WorkingTimeModes?>?,
                      val accept_temporary: Boolean?,
                      @SerialName("immediate_redirect_url") val immediate_redirect_url: String? = null)
@kotlinx.serialization.Serializable
data class Department(val id: String?, val name: String?)
@kotlinx.serialization.Serializable
data class TypeVacancy(val id: String?, val name: String?)
@kotlinx.serialization.Serializable
data class AddressRequest (val city: String?,
                           val street: String?,
                           val building: String?,
                           val description: String?,
                           val lat: Double?,
                           val lng: Double?,
                           val raw: String?,
                           val metro: String?,
                           val metro_stations: List<NNUL?>,
                           val id: Int?)
@kotlinx.serialization.Serializable
data class ShortSalary(val from: Int?, val to: Int?, val currency: String?, val gross: Boolean?)
@kotlinx.serialization.Serializable
data class Employer(val id: Int?, val name: String?, val url: String?, val alternate_url: String?, val logo_urls: LogoUrls?, val vacancies_url: String?, val trusted: Boolean?)
@kotlinx.serialization.Serializable
data class LogoUrls(@SerialName("240")val size_240: String?, @SerialName("90") val size_90: String?, @SerialName("original") val original: String?)
@kotlinx.serialization.Serializable
data class Snippet(val requirement: String?, val responsibility: String?)
@kotlinx.serialization.Serializable
data class Schedule(val id: String?, val name: String?)
@kotlinx.serialization.Serializable
data class NNUL(val str: String?)
@kotlinx.serialization.Serializable
data class  WorkingDays(val id: String?, val name: String?)
@kotlinx.serialization.Serializable
data class  WorkingTimeIntervals(val id: String?, val name: String?)
@kotlinx.serialization.Serializable
data class  WorkingTimeModes(val id: String?, val name: String?)
@kotlinx.serialization.Serializable
data class  InsiderInterview(val id: String?, val url: String?)