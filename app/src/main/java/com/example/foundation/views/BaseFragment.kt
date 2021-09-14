package com.example.foundation.views

import androidx.fragment.app.Fragment
import com.example.hhapitest.MainActivity

abstract class BaseFragment: Fragment() {
    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates(){
        (requireActivity() as MainActivity).notifyScreenUpdates()
    }
}