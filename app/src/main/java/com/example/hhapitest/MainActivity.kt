package com.example.hhapitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foundation.ActivityScopeViewModel
import com.example.foundation.navigator.IntermediateNavigator
import com.example.foundation.navigator.StackFragmentNavigator
import com.example.foundation.uiactions.AndroidUIActions
import com.example.foundation.utils.viewModelCreator
import com.example.foundation.views.FragmentsHolder
import com.example.hhapitest.databinding.ActivityMainBinding
import com.example.hhapitest.databinding.CreateRequestBinding
import com.example.hhapitest.views.createrequest.CreateRequest
import com.example.hhapitest.views.listofroomrequest.ListOfRoomRequest
import com.example.hhapitest.views.requestlist.RequestList

class MainActivity : AppCompatActivity(), FragmentsHolder {
    private val mainViewModel by viewModelCreator<ActivityScopeViewModel> {
        ActivityScopeViewModel(
            uiActions = AndroidUIActions(applicationContext),
            navigator = IntermediateNavigator()
        )
    }
    //private val actions = mutableListOf<() -> Unit>()
    lateinit var binding: ActivityMainBinding// binding 1
    private lateinit var navigator: StackFragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)// binding 2
        setContentView(binding.root)// binding 3
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        navigator = StackFragmentNavigator(
            activity = this,
            idContainer = R.id.fragment_container,
            defaultTitle = this.getString(R.string.app_name),
            animations = null,
            initialScreeCreator = {ListOfRoomRequest.Screen()}
        )
        navigator.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        navigator.onDestroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.navigator.setTarget(navigator)
    }

    override fun onPause() {
        super.onPause()

        mainViewModel.navigator.setTarget(null)
    }

    override fun notifyScreenUpdates() {
        navigator.notifyScreenUpdates()
    }

    override fun getActivityScopeViewModel(): ActivityScopeViewModel {
        return mainViewModel
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
}