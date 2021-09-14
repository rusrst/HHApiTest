package com.example.hhapitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.hhapitest.foundation.views.HasScreenTitle
import com.example.hhapitest.databinding.ActivityMainBinding
import com.example.hhapitest.foundation.ActivityScopeViewModel
import com.example.hhapitest.foundation.views.BaseFragment
import com.example.hhapitest.views.requestlist.RequestList

class MainActivity : AppCompatActivity() {
    val mainViewModel by viewModels<ActivityScopeViewModel>{ViewModelProvider.AndroidViewModelFactory(application)}
    //private val actions = mutableListOf<() -> Unit>()
    lateinit var binding: ActivityMainBinding// binding 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)// binding 2
        setContentView(binding.root)// binding 3
        if (savedInstanceState == null){
            mainViewModel.launchFragment(
                this,
                RequestList.Screen(),
                false)
        }
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.whenActivityActive.resource = this
        //actions.forEach { it() }
        //actions.clear()
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.whenActivityActive.resource = null
    }

/*
    private fun runWhenActive(action: () -> Unit) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            // activity is active -> just execute the action
            action()
        } else {
            // activity is not active -> add action to queue
            actions += action
        }
    }

 */

    fun notifyScreenUpdates(){
        val f = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (supportFragmentManager.backStackEntryCount > 0) supportActionBar?.setDisplayHomeAsUpEnabled(true)
        else supportActionBar?.setDisplayHomeAsUpEnabled(false)

        if (f is HasScreenTitle && f.getScreenTitle() !=null) {
            supportActionBar?.title = f.getScreenTitle()
        }
        else supportActionBar?.title = getString(R.string.app_name)

        val result = mainViewModel.result.value?.getValue() ?: return
        if (f is BaseFragment){
            f.viewModel.onResult(result)
        }

    }

    private val fragmentCallbacks = object: FragmentManager.FragmentLifecycleCallbacks(){
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            notifyScreenUpdates()
        }
    }
}