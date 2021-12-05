package com.example.hhapitest.views.createrequest

import android.content.DialogInterface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import com.example.foundation.model.SuccessResult
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel
import com.example.hhapitest.R
import com.example.hhapitest.databinding.CreateRequestBinding
import com.example.hhapitest.databinding.PartResultBinding
import com.example.hhapitest.model.data.AreaRoom
import com.example.hhapitest.views.CustomAutoCompleteAdapter
import com.example.hhapitest.views.SimpleDialogFragment
import com.example.hhapitest.views.renderSimpleResult
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.IllegalArgumentException


class CreateRequest(): BaseFragment() {
    var currentString: String? = null
    private val buildingRequest = BuildingRequest()
    override val viewModel by screenViewModel<CreateRequestViewModel>()
    class Screen : BaseScreen
    var string = ""
    private lateinit var binding: CreateRequestBinding

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

        val adapter = CustomAutoCompleteAdapter(requireContext(), viewModel)
        binding.addCityEditText.setAdapter(adapter)
        binding.addCityEditText.setOnItemClickListener { _, _, _, id ->
            binding.addCityEditText.text = SpannableStringBuilder("")
            buildingRequest.areas.add(adapter.getListItemById(id.toInt()))
            val inflater = LayoutInflater.from(requireContext())
            val newChip = inflater.inflate(R.layout.chip_create_request, binding.createRequestChipGroup, false) as Chip
            newChip.text = adapter.getListItemById(id.toInt()).name
            binding.createRequestChipGroup.addView(newChip)
            newChip.setOnCloseIconClickListener {
                val parent = it.parent as ChipGroup
                parent.removeView(it)
                buildingRequest.deleteAreasByString((it as Chip).text.toString())
            }
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
    data class BuildingRequest (val areas: MutableList<AreaRoom> = mutableListOf()){
        fun deleteAreasByString (name: String){
            val id = getIdAreasByString(name)
            if (id != null) areas.removeAt(id)
            else throw IllegalArgumentException("NOT INDEX BUILDINGREQUEST")
        }
        private fun getIdAreasByString (name:String): Int?{
            if (areas.size != 0){
                for (i in 0 until areas.size){
                    if (areas[i].name == name) return i
                }
            }
            return null
        }
    }
}