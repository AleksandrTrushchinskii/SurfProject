package ru.aleksandrtrushchinskii.surfproject.ui.component

import android.os.Bundle
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.ui.MainActivity
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.*


object Navigation {

    private var activity: MainActivity? = null
    private var currentFragment: String = ""


    fun init(activity: MainActivity) {
        Navigation.activity = activity
    }

    fun clear() {
        activity = null
    }

    fun start() {
        if (currentFragment.isNotEmpty()) {
            startFragment(currentFragment)
        } else {
            startFragment(SearchFragment::class.java.simpleName)
        }
    }

    fun finishCurrentFragment() {
        activity ?: return

        LoadingState.start()

        when (currentFragment) {
            SignInFragment::class.java.simpleName -> startFragment(SearchFragment::class.java.simpleName)
            CreateFragment::class.java.simpleName -> startFragment(SearchFragment::class.java.simpleName)
            EditFragment::class.java.simpleName -> startFragment(ReadFragment::class.java.simpleName)
            ReadFragment::class.java.simpleName -> startFragment(SearchFragment::class.java.simpleName)
            else -> throw RuntimeException("Unknown fragment finish : $currentFragment")
        }
    }

    fun startFragment(fragmentClassName: String, bundle: Bundle? = null) {
        activity ?: return

        when (fragmentClassName) {
            SignInFragment::class.java.simpleName -> {
                activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SignInFragment())
                        .commit()
            }
            CreateFragment::class.java.simpleName -> {
                with(activity!!.supportFragmentManager) {
                    popBackStack()
                    beginTransaction()
                            .replace(R.id.container, CreateFragment())
                            .addToBackStack(null)
                            .commit()
                }
            }
            SearchFragment::class.java.simpleName -> {
                activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment())
                        .commit()
            }
            ReadFragment::class.java.simpleName -> {
                val readFragment = ReadFragment().apply {
                    arguments = bundle
                }

                with(activity!!.supportFragmentManager) {
                    popBackStack()
                    beginTransaction()
                            .replace(R.id.container, readFragment)
                            .addToBackStack(null)
                            .commit()
                }
            }
            EditFragment::class.java.simpleName -> {
                with(activity!!.supportFragmentManager) {
                    popBackStack()
                    beginTransaction()
                            .replace(R.id.container, EditFragment())
                            .addToBackStack(null)
                            .commit()
                }
            }
            else -> throw RuntimeException("Unknown fragment start : $fragmentClassName")
        }

        currentFragment = fragmentClassName
    }

}