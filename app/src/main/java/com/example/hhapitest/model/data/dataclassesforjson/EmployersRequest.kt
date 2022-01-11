package com.example.hhapitest.model.data.dataclassesforjson

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class EmployersRequest(@SerialName("per_page") val perPage: Int?,
                            @SerialName("page") val page: Int?,
                            @SerialName("pages") val pages: Int?,
                            @SerialName("found") val found: Int?,
                            @SerialName("items") val items: List<EmployerRequest>?)

@kotlinx.serialization.Serializable
data class EmployerRequest(@SerialName("id") val id: String?,
                           @SerialName("name") val name: String?,
                           @SerialName("url") val url: String?,
                           @SerialName("alternate_url") val alternate_url: String?,
                           @SerialName("vacancies_url") val vacancies_url: String?,
                           @SerialName("open_vacancies") val open_vacancies: Int?,
                           @SerialName("logo_urls") val logoUrls: LogoUrls?)
