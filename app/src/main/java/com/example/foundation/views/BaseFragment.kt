package com.example.foundation.views

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.foundation.model.ErrorResult
import com.example.foundation.model.PendingResult
import com.example.foundation.model.Result
import com.example.foundation.model.SuccessResult

abstract class BaseFragment: Fragment() {
    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates(){
        (requireActivity() as FragmentsHolder).notifyScreenUpdates()
    }

    fun <T> renderResult(root: ViewGroup, result: Result<T>,
                        onPending: ()->Unit,
                        onError: (Exception) -> Unit,
                        onSuccess: (T)->Unit){
        root.children.forEach {
            it.visibility = View.GONE
        }
        when (result){
            is PendingResult -> onPending()
            is ErrorResult -> onError(result.exception)
            is SuccessResult -> onSuccess(result.data)
        }
    }
}