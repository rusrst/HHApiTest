package com.example.hhapitest.views.createrequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel
import com.example.hhapitest.R
import com.example.hhapitest.databinding.CreateRequestBinding
import com.example.hhapitest.databinding.PartResultBinding
import com.example.hhapitest.model.data.Area
import com.example.hhapitest.views.renderSimpleResult


class CreateRequest(): BaseFragment() {
    override val viewModel by screenViewModel<CreateRequestViewModel>()
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

/*
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
    */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListAreas()
        val resultBinding = PartResultBinding.bind(binding.root)
        viewModel.data.observe(viewLifecycleOwner){ result ->

            renderSimpleResult(root = binding.root,
                result = result,
                onSuccess = {
                    val myTextView = layoutInflater.inflate(R.layout.round_textview, null) as TextView
                    myTextView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    myTextView.text = it.toString()
                    binding.createRequestScrollView.addView(myTextView)
                    viewModel.addAreaRoomList(it)
                    notifyScreenUpdates()
                })
        }
        resultBinding.tryAgainButton.setOnClickListener {
            viewModel.tryAgain{ viewModel.getListAreas() }
        }
    }

}