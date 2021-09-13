package com.example.hhapitest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hhapitest.databinding.CreateRequestBinding
import com.example.hhapitest.model.data.ListRequest
import com.example.hhapitest.model.repository.HhApiDataInternet
import com.google.gson.Gson

class CreateRequest(): Fragment() {
    var string = ""
    private lateinit var binding: CreateRequestBinding
    val hhApiDataInternet by lazy<HhApiDataInternet> {  HhApiDataInternet() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateRequestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = hhApiDataInternet.getListRequestFromUrl("https://api.hh.ru/vacancies?area=1531&period=1")
        /*
        val gson = Gson()
        val data = gson.fromJson("{\"items\":[" +
                "{\"id\":\"46742058\",\"premium\":false,\"name\":\"Инструктор по обучению вождению категории В на АКПП\",\"department\":null,\"has_test\":false,\"response_letter_required\":false,\"area\":{\"id\":\"1531\",\"name\":\"Азов\",\"url\":\"https://api.hh.ru/areas/1531\"},\"salary\":{\"from\":50000,\"to\":null,\"currency\":\"RUR\",\"gross\":false},\"type\":{\"id\":\"open\",\"name\":\"Открытая\"},\"address\":{\"city\":\"Азов\",\"street\":\"Петровский бульвар\",\"building\":\"5А\",\"description\":null,\"lat\":47.110841,\"lng\":39.422036,\"raw\":\"Азов, Петровский бульвар, 5А\",\"metro\":null,\"metro_stations\":[],\"id\":\"5826516\"},\"response_url\":null,\"sort_point_distance\":null,\"published_at\":\"2021-08-30T12:48:27+0300\",\"created_at\":\"2021-08-30T12:48:27+0300\",\"archived\":false,\"apply_alternate_url\":\"https://hh.ru/applicant/vacancy_response?vacancyId=46742058\",\"insider_interview\":null,\"url\":\"https://api.hh.ru/vacancies/46742058?host=hh.ru\",\"alternate_url\":\"https://hh.ru/vacancy/46742058\",\"relations\":[],\"employer\":{\"id\":\"1126088\",\"name\":\"ЧОУ ПО Автошкола С. А. Союз Автошкол\",\"url\":\"https://api.hh.ru/employers/1126088\",\"alternate_url\":\"https://hh.ru/employer/1126088\",\"logo_urls\":{\"original\":\"https://hhcdn.ru/employer-logo-original/791336.png\",\"90\":\"https://hhcdn.ru/employer-logo/3606231.png\",\"240\":\"https://hhcdn.ru/employer-logo/3606232.png\"},\"vacancies_url\":\"https://api.hh.ru/vacancies?employer_id=1126088\",\"trusted\":true},\"snippet\":{\"requirement\":\"Ознакомление ученика первичным знаниям по эксплуатации и ремонту автомобиля. Проведение дополнительных и индивидуальных занятий по корректировке навыков вождения. \",\"responsibility\":\"Проведение занятий по подготовке водителей транспортных средств категории В.\"},\"contacts\":null,\"schedule\":{\"id\":\"flexible\",\"name\":\"Гибкий график\"},\"working_days\":[],\"working_time_intervals\":[],\"working_time_modes\":[],\"accept_temporary\":true},\n" +
                "]," +
                "\"found\":34,\"pages\":2,\"per_page\":20,\"page\":0,\"clusters\":null,\"arguments\":null,\"alternate_url\":\"https://hh.ru/search/vacancy?area=1531&enable_snippets=true&search_period=1\"}", ListRequest::class.java)
        binding.outputTextView.text = data.found.toString()

         */
        ///*
        data.observe(viewLifecycleOwner, {
            val gson = Gson()
            val data = gson.fromJson(it, ListRequest::class.java)
            binding.outputTextView.text = data.found.toString()
        })

        // */
    }
}