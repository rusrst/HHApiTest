package com.example.foundation

import android.app.Application
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.example.hhapitest.MainActivity
import com.example.hhapitest.R
import com.example.foundation.utils.Event
import com.example.foundation.utils.ResourceActions
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.LiveEvent
import com.example.foundation.views.MutableLiveEvent


const val ARG_SCREEN = "ARG_SCREEN"


class ActivityScopeViewModel(application: Application): AndroidViewModel(application), UIActions, Navigator {
    val whenActivityActive = ResourceActions<MainActivity>()


    private val _result = MutableLiveEvent<Any>()
    val result: LiveEvent<Any> = _result

    override fun launch(screen: BaseScreen) = whenActivityActive{
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive{
        if (result != null){
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    override fun toast(message: String) {
        TODO("Not yet implemented")
    }

    override fun getString(messageRes: Int, vararg args: Any): String {
        TODO("Not yet implemented")
    }
    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = true){
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        // set screen object as fragment's argument
        fragment.arguments = bundleOf(ARG_SCREEN to screen)

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }
}