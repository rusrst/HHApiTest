package com.example.hhapitest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.foundation.views.BaseScreen
import com.example.hhapitest.R
import com.example.hhapitest.databinding.CreateRequestBinding


class CreateRequest(): Fragment() {
    class Screen : BaseScreen
    var string = ""
    private lateinit var binding: CreateRequestBinding

    //val hhApiDataInternet by lazy<HhApiDataInternet> {  HhApiDataInternet() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateRequestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addCityButton.setOnClickListener {
            if (binding.addCityEditText.editableText.toString() != ""){
                val myTextView = layoutInflater.inflate(R.layout.round_textview, null) as TextView
                myTextView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                myTextView.text = binding.addCityEditText.editableText.toString()
                binding.LinearLayoutAddCity.addView(myTextView)
                myTextView.setOnClickListener {
                    binding.LinearLayoutAddCity.removeView(it)
                }
            }
        }
    }
}