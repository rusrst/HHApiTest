package com.example.hhapitest.views.createrequest

import android.animation.ValueAnimator
import android.content.DialogInterface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.FragmentResultListener
import com.example.foundation.model.SuccessResult
import com.example.foundation.utils.getMeasureSize
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel
import com.example.hhapitest.R
import com.example.hhapitest.databinding.CreateRequestBinding
import com.example.hhapitest.databinding.PartResultBinding
import com.example.hhapitest.model.database.dataclassroom.AreaRoom
import com.example.hhapitest.model.json.dataclassesforjson.EmployerRequest
import com.example.hhapitest.views.CustomAutoCompleteAdapterAreas
import com.example.hhapitest.views.CustomAutoCompleteAdapterEmployers
import com.example.hhapitest.views.SimpleDialogFragment
import com.example.hhapitest.views.renderSimpleResult
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.IllegalArgumentException


class CreateRequest(): BaseFragment() {

    private val buildingRequest = BuildingRequest()
    override val viewModel by screenViewModel<CreateRequestViewModel>()
    class Screen : BaseScreen

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
        binding.textViewEnterNameCity.visibility = View.GONE
        viewModel.checkingDatabaseOfAreas.observe(viewLifecycleOwner){result ->
            viewModel.checkDatabaseOfAreasEnd(result)
        }
        viewModel.state.observe(viewLifecycleOwner){
            if (it.showDialog){
                val simpleDialog = SimpleDialogFragment()
                val args = Bundle()
                args.putString("title", "${it.dataSimpleDialog?.title}")
                args.putString("text", "${it.dataSimpleDialog?.text}")
                simpleDialog.arguments = args
                simpleDialog.show(requireActivity().supportFragmentManager, SimpleDialogFragment.TAG)
            }
        }

        val adapterAreas = CustomAutoCompleteAdapterAreas(requireContext(), viewModel)
        val adapterEmployers = CustomAutoCompleteAdapterEmployers(requireContext(), viewModel)
        binding.addCityEditTextEmployers.setAdapter(adapterEmployers)
        binding.addCityEditTextEmployers.setOnItemClickListener { _, _, _, id ->
            listenerSelectEmployer(adapterEmployers, id)
        }
        binding.addCityEditTextAreas.setAdapter(adapterAreas)
        binding.addCityEditTextAreas.setOnItemClickListener { _, _, _, id ->
            listenerSelectArea(adapterAreas, id)
        }
        binding.addCityEditTextAreas.setOnFocusChangeListener { v, hasFocus ->
                listenerOnFocusChange (v, hasFocus)
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
                                resultBinding.tryAgainButton.setOnClickListener(null)
                                binding.textViewEnterNameCity.visibility = View.GONE
                            })
                    }
                    resultBinding.tryAgainButton.setOnClickListener {
                        viewModel.tryAgain{ viewModel.getListAreas() }
                    }

                }
            }
        })
    }



    private fun deleteChip (it:View){
        val parent = it.parent as ChipGroup
        parent.removeView(it)
        buildingRequest.deleteAreasByString((it as Chip).text.toString())
    }

    private fun listenerSelectArea (adapterCity: CustomAutoCompleteAdapterAreas, id: Long){
        binding.addCityEditTextAreas.text = SpannableStringBuilder("")
        buildingRequest.areas.add(adapterCity.getListItemById(id.toInt()))
        val inflater = LayoutInflater.from(requireContext())
        val newChip = inflater.inflate(R.layout.chip_create_request, binding.createRequestChipGroup, false) as Chip
        newChip.text = adapterCity.getListItemById(id.toInt()).name
        val currentHeight = binding.createRequestChipGroup.height
        binding.createRequestChipGroup.addView(newChip)
        binding.createRequestChipGroup.measure(0,3)



        newChip.setOnCloseIconClickListener {
            deleteChip(it)
        }
        if (currentHeight != binding.createRequestChipGroup.measuredHeight){
            var step = 0
            if (binding.createRequestChipGroup.measuredHeight%5 == 0) step = binding.createRequestChipGroup.measuredHeight/5
            else{
                val tempHeight = binding.createRequestChipGroup.measuredHeight%5
                step = binding.createRequestChipGroup.measuredHeight/5
                val tempParam = binding.createRequestChipGroup.layoutParams.apply {
                    height = tempHeight
                }
                binding.createRequestChipGroup.layoutParams = tempParam
            }

            val animator = ValueAnimator.ofInt(binding.createRequestChipGroup.layoutParams.height, binding.createRequestChipGroup.measuredHeight)
            animator.duration = 250
            animator.addUpdateListener {
                val value = it.animatedValue as Int
                val lp = binding.createRequestChipGroup.layoutParams
                lp.height = value
                binding.createRequestChipGroup.layoutParams = lp
            }
            animator.start()
        }
    }

    private fun listenerSelectEmployer (adapterEmployers: CustomAutoCompleteAdapterEmployers, id: Long){
        binding.addCityEditTextEmployers.text = SpannableStringBuilder("")
        buildingRequest.employers.add(adapterEmployers.getListItemById(id.toInt()))
    }

    private fun listenerOnFocusChange (v: View, hasFocus: Boolean){
        var newHeight = 0
        var oldHeight = 0

        if (hasFocus) {
            binding.textViewEnterNameCity.visibility = View.VISIBLE
            oldHeight = 0
            binding.textViewEnterNameCity.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            newHeight = binding.textViewEnterNameCity.getMeasureSize(binding.createRequestConstrainLayout).height
            val animator = ValueAnimator.ofInt(oldHeight, newHeight)
            animator.duration = 800
            animator.addUpdateListener {
                binding.textViewEnterNameCity.height = it.animatedValue as Int
            }
            animator.doOnEnd {
            }
            animator.start()
        }
        else {
            binding.textViewEnterNameCity.visibility = View.GONE
            oldHeight = binding.textViewEnterNameCity.height
            binding.textViewEnterNameCity.visibility = View.VISIBLE
            newHeight = 0
            val animator = ValueAnimator.ofInt(oldHeight, newHeight)
            animator.duration = 800
            animator.addUpdateListener {
                binding.textViewEnterNameCity.height = it.animatedValue as Int
            }
            animator.doOnEnd {
                binding.textViewEnterNameCity.height = oldHeight
                binding.textViewEnterNameCity.visibility = View.GONE
            }
            animator.start()
        }
    }

    data class BuildingRequest (val areas: MutableList<AreaRoom> = mutableListOf(),
                                val employers: MutableList<EmployerRequest> = mutableListOf(),
    ) {
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