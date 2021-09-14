package com.example.hhapitest.views.requestlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hhapitest.RequestAdapter
import com.example.hhapitest.databinding.RequestListBinding
import com.example.hhapitest.model.data.ListRequest
import com.example.foundation.views.HasScreenTitle
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel
import com.google.gson.Gson

class RequestList : BaseFragment(), HasScreenTitle {

    class Screen : BaseScreen


    private lateinit var binding: RequestListBinding
    private lateinit var adapter: RequestAdapter
    override val viewModel by screenViewModel<RequestListViewModel>()
    private var title: String = "List"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RequestListBinding.inflate(inflater, container, false)
        adapter = RequestAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.requestList.layoutManager = layoutManager
        binding.requestList.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, {
            val gson = Gson()
            val data = gson.fromJson(it, ListRequest::class.java)
             adapter.items = data.items ?: emptyList()
            title = "In page = ${data.items?.size ?: 0}"
            notifyScreenUpdates()
        })
        return binding.root
    }

    override fun getScreenTitle(): String {
        return title
    }
}
