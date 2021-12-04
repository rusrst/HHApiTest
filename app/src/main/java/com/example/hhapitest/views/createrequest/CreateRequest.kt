package com.example.hhapitest.views.createrequest

import android.content.DialogInterface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentResultListener
import com.example.foundation.model.SuccessResult
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel
import com.example.hhapitest.R
import com.example.hhapitest.databinding.CreateRequestBinding
import com.example.hhapitest.databinding.PartResultBinding
import com.example.hhapitest.views.CustomAutoCompleteAdapter
import com.example.hhapitest.views.SimpleDialogFragment
import com.example.hhapitest.views.renderSimpleResult
import com.google.android.material.chip.Chip


class CreateRequest(): BaseFragment() {
    var currentString: String? = null
    override val viewModel by screenViewModel<CreateRequestViewModel>()
    class Screen : BaseScreen
    var string = ""
    private lateinit var binding: CreateRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        setupSimpleDialogFragmentListener()
        viewModel.checkDatabaseOfAreas()
        renderSimpleResult(root = binding.root,
            result = SuccessResult(true),
            onSuccess = {})
        viewModel.checkingDatabaseOfAreas.observe(viewLifecycleOwner){result ->
            viewModel.checkDatabaseOfAreasEnd(result)
        }
        viewModel.state.observe(viewLifecycleOwner){
            if (it.showDialog){
                val simpleDialog = SimpleDialogFragment(it.dataSimpleDialog?.title, it.dataSimpleDialog?.text)
                simpleDialog.show(requireActivity().supportFragmentManager, SimpleDialogFragment.TAG)
            }
        }

        var adapter = CustomAutoCompleteAdapter(requireContext(), viewModel)
        binding.addCityEditText.setAdapter(adapter)
        binding.addCityEditText.setOnItemClickListener { _, _, _, id ->
            binding.addCityEditText.text = SpannableStringBuilder(adapter.getListItemById(id.toInt()).name)
            currentString = adapter.getListItemById(id.toInt()).name
            val inflater = LayoutInflater.from(requireContext())
            val newChip = inflater.inflate(R.layout.chip_create_request, binding.createRequestChipGroup, false) as Chip
            newChip.text = currentString
            binding.createRequestChipGroup.addView(newChip)
        }
    }


    private fun setupSimpleDialogFragmentListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(SimpleDialogFragment.KEY, viewLifecycleOwner, FragmentResultListener { _, result ->
            when (result.getInt(SimpleDialogFragment.TAG)) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val resultBinding = PartResultBinding.bind(binding.root)
                    viewModel.getListAreas()
                    viewModel.listAreas.observe(viewLifecycleOwner){ itResult ->
                        renderSimpleResult(root = binding.root,
                            result = itResult,
                            onSuccess = {
                                viewModel.addAreaRoomList(it)
                                resultBinding.tryAgainButton.setOnClickListener(null)
                            })
                    }
                    resultBinding.tryAgainButton.setOnClickListener {
                        viewModel.tryAgain{ viewModel.getListAreas() }
                    }

                }
            }
        })
    }


}