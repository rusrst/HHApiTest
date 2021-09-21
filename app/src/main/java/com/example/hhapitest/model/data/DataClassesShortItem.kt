package com.example.hhapitest.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class ListRequest ( var items: List<ShortItem>?,
                        var found: Int?,
                        var pages: Int?,
                        var per_page: Int?,
                        var page: Int?,
                        var clasters: String?,
                        var arguments: String?,
                        val alternate_url: String?)

data class ShortItem (val id: Int?,
                      val premium: Boolean?,
                      val name: String?,
                      val department: Department?,
                      val has_test: Boolean?,
                      val response_letter_required: Boolean?,
                      val area: ShortArea?,
                      val salary: ShortSalary?,
                      val type: TypeVacancy?,
                      val address: AdressRequest?,
                      val response_url: String?,
                      val sort_point_distance: String?,
                      val published_at: String?,
                      val created_at: String?,
                      val archived: Boolean?,
                      val apply_alternate_url: String?,
                      val insider_interview: String?,
                      val url: String?,
                      val alternate_url: String?,
                      val relations: List<NNUL?>,
                      val employer: Employer?,
                      val snippet: Snippet?,
                      val contacts: String?,
                      val schedule: Schedule?,
                      val working_days: List<NNUL?>,
                      val working_time_intervals: List<NNUL?>,
                      val working_time_modes: List<NNUL?>,
                      val accept_temporary: Boolean?) : Serializable

data class Department(val id: String?, val name: String?)

data class TypeVacancy(val id: String, val name: String)
data class AdressRequest (val city: String?,
                          val street: String?,
                          val building: String?,
                          val description: String?,
                          val lat: Double?,
                          val lng: Double?,
                          val raw: String?,
                          val metro: String?,
                          val metro_stations: List<NNUL?>,
                          val id: Int)

data class Employer(val id: Int, val name: String, val url: String, val alternate_url: String, val logo_urls: LogoUrls, val vacancies_url: String, val trusted: Boolean)

data class LogoUrls(@SerializedName("240")val size_240: String, @SerializedName("90") val size_90: String, val original: String)

data class Snippet(val requirement: String, val responsibility: String)

data class Schedule(val id: String, val name: String)

data class NNUL(val str: String?)