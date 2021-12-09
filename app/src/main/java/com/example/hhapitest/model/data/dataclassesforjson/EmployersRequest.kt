package com.example.hhapitest.model.data.dataclassesforjson

import com.google.gson.annotations.SerializedName

data class EmployersRequest(@SerializedName("per_page") val perPage: String,
                            @SerializedName("page") val page: String,
                            @SerializedName("pages") val pages: String,
                            @SerializedName("found") val found: String,
                            @SerializedName("items") val items: List<EmployerRequest>?)


data class EmployerRequest(@SerializedName("id") val id: String,
                           @SerializedName("name") val name: String,
                           @SerializedName("url") val url: String,
                           @SerializedName("alternate_url") val alternate_url: String,
                           @SerializedName("vacancies_url") val vacancies_url: String,
                           @SerializedName("open_vacancies") val open_vacancies: String,
                           @SerializedName("logo_urls") val logoUrls: LogoUrls)
