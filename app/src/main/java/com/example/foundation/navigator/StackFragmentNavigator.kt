package com.example.foundation.navigator

import android.os.Bundle
import android.view.View
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.foundation.ARG_SCREEN
import com.example.foundation.ARG_STARTUP
import com.example.foundation.utils.Event
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.HasScreenTitle
import java.io.Serializable

class StackFragmentNavigator(private val activity: AppCompatActivity,
                             @IdRes private val idContainer: Int,
                             private val defaultTitle: String,
                             private val animations: Animations?,
                             private val initialScreeCreator: () -> BaseScreen,
                            ) : Navigator {
    private var result : Event<Any>? = null
    override fun launch(screen: BaseScreen, result: Any?){
        launchFragment(screen, result = result)
    }

    override fun goBack(result: Any?) {
        if (result != null){
            this.result = Event(result)
        }
        activity.onBackPressed()
    }

    fun onCreate(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            launchFragment(
                initialScreeCreator(),
                false)
        }
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    fun onDestroy(){
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    fun launchFragment(screen: BaseScreen, addToBackStack: Boolean = true, result: Any? = null){
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        // set screen object as fragment's argument
        var bundle = Bundle().apply{putSerializable(ARG_SCREEN, screen)}
        if (result != null){
            bundle.putSerializable(ARG_STARTUP, result as Serializable) // WARRNING, THIS CODE MUST BE SEIALIZABLE
        }
        fragment.arguments = bundle
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        if (animations != null){
            transaction
                .setCustomAnimations(
                    animations.enterAnim,
                    animations.exitAnim,
                    animations.popEnterAnim,
                    animations.popExitAnim,
                )}
                transaction
                .replace(idContainer, fragment)
                .commit()

    }

    fun notifyScreenUpdates(){
        val f = activity.supportFragmentManager.findFragmentById(idContainer)

        if (activity.supportFragmentManager.backStackEntryCount > 0) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setDisplayShowHomeEnabled(true)
        }
        else{
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            activity.supportActionBar?.setDisplayShowHomeEnabled(false)
        }

        if (f is HasScreenTitle && f.getScreenTitle() !=null) {
            activity.supportActionBar?.title = f.getScreenTitle()
        }
        else activity.supportActionBar?.title = defaultTitle
    }

    fun publishResults(f: Fragment){
        val result = result?.getValue() ?: return
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
            publishResults(f)
        }
    }

    class Animations(
        @AnimRes val enterAnim: Int,
        @AnimRes val exitAnim: Int,
        @AnimRes val popEnterAnim: Int,
        @AnimRes val popExitAnim: Int,
    )
}