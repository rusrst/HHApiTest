package com.example.hhapitest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foundation.ARG_STARTUP
import com.example.foundation.views.*
import com.example.hhapitest.databinding.DetailsItemBinding
import com.example.hhapitest.databinding.RequestListBinding
import com.example.hhapitest.model.data.ShortItem
import com.example.hhapitest.views.requestlist.RequestListViewModel
import java.util.*

class DetailsFragment(): BaseFragment(), HasScreenTitle {
    class Screen : BaseScreen
    override val viewModel by screenViewModel<DetailsViewModel>()
    override fun getScreenTitle(): String {
        return Date().toString()
    }
    private lateinit var binding: DetailsItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val detailsItem = arguments?.getSerializable(ARG_STARTUP) as ShortItem
        binding.name.text = detailsItem.name ?: ""
        binding.city.text = detailsItem.address?.city
        binding.adress.text = detailsItem.address?.street
        binding.from.text = detailsItem.salary?.from.toString()
        binding.currency.text = detailsItem.salary?.currency
        binding.to.text = detailsItem.salary?.to.toString()
        binding.requirementDetails.text = detailsItem.snippet?.requirement
        binding.responsibilityDetails.text = detailsItem.snippet?.responsibility
    }
}