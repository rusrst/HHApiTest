package com.example.hhapitest.views.listofroomrequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foundation.model.SuccessResult
import com.example.foundation.views.*
import com.example.hhapitest.databinding.ListofroomrequestBinding
import com.example.hhapitest.databinding.PartResultBinding
import com.example.hhapitest.views.renderSimpleResult

class ListOfRoomRequest : BaseFragment(), HasScreenTitle {
    class Screen : BaseScreen


    private lateinit var binding: ListofroomrequestBinding
    private lateinit var adapter: ListOfRoomRequestAdapter
    override val viewModel by screenViewModel<ListOfRoomRequestViewModel>()
    private var title: String = "List"

    override fun getScreenTitle(): String {
        return title
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ListofroomrequestBinding.inflate(inflater, container, false)
        adapter = ListOfRoomRequestAdapter(viewModel)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.listOfRoomRequest.layoutManager = layoutManager
        binding.listOfRoomRequest.adapter = adapter
        val resultBinding = PartResultBinding.bind(binding.root)
        

        viewModel.getListOfRequests().observe(viewLifecycleOwner, {list ->

            renderSimpleResult(root = binding.root,
                result = SuccessResult(list),
                onSuccess = {
                    adapter.items = it ?: emptyList()
                    title = "In page = ${it?.size ?: 0}"
                    notifyScreenUpdates()
                })
        })
        return binding.root
    }
}
