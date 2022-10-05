package com.example.hhapitest.views.detailsfragment

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.foundation.ARG_STARTUP
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.HasScreenTitle
import com.example.foundation.views.screenViewModel
import com.example.hhapitest.databinding.DetailsItemBinding
import com.example.hhapitest.databinding.PartResultBinding
import com.example.hhapitest.views.renderSimpleResult
import java.util.*

class DetailsFragment() : BaseFragment(), HasScreenTitle {
    class Screen : BaseScreen

    override val viewModel by screenViewModel<DetailsViewModel>()
    override fun getScreenTitle(): String {
        return Date().toString()
    }

    private lateinit var binding: DetailsItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null || viewModel.urlItem == null) viewModel.urlItem =
            (arguments?.getSerializable(ARG_STARTUP) as String?)?.substringBefore("?")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsItemBinding.inflate(inflater, container, false)
        val resultBinding = PartResultBinding.bind(binding.root)
        resultBinding.tryAgainButton.setOnClickListener {
            viewModel.tryAgain()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (viewModel.urlItem != null) {
            viewModel.getStringData(viewModel.urlItem ?: "")
        }
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
        viewModel.liveBigItem.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(root = binding.root,
                result = result,
                onSuccess = {
                    val str = Base64.encodeToString(it?.toByteArray(), Base64.NO_PADDING)
                    binding.webView.loadData(str, "text/html", "base64")
                })
        }
        super.onViewCreated(view, savedInstanceState)
    }
}