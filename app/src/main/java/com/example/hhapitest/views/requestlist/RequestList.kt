package com.example.hhapitest.views.requestlist

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foundation.model.ErrorResult
import com.example.foundation.model.PendingResult
import com.example.foundation.model.SuccessResult
import com.example.hhapitest.RequestAdapter
import com.example.hhapitest.databinding.RequestListBinding
import com.example.hhapitest.model.data.ListRequest
import com.example.foundation.views.HasScreenTitle
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel
import com.example.hhapitest.databinding.PartResultBinding
import com.example.hhapitest.views.renderSimpleResult
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
        adapter = RequestAdapter(viewModel)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.requestList.layoutManager = layoutManager
        binding.requestList.adapter = adapter
        val resultBinding = PartResultBinding.bind(binding.root)

        viewModel.getListRequestFromUrl()
        viewModel.liveListShortItem.observe(viewLifecycleOwner, {result ->

            renderSimpleResult(root = binding.root,
            result = result,
            onSuccess = {
                adapter.items = it ?: emptyList()
                title = "In page = ${it?.size ?: 0}"
                notifyScreenUpdates()
            })
        })


        return binding.root
    }

    override fun getScreenTitle(): String {
        return title
    }
}
