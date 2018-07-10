package ru.aleksandrtrushchinskii.surfproject.ui.component

import android.os.Bundle
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.ui.MainActivity
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.CreateEditFragment
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.ReadFragment
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.SearchFragment
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.SignInFragment


object Navigation {

    private var activity: MainActivity? = null
    private var currentFragment: String = ""
    private var currentBundle: Bundle? = null


    fun init(activity: MainActivity) {
        Navigation.activity = activity
    }

    fun clear() {
        activity = null
    }

    fun start() {
        if (currentFragment.isNotEmpty()) {
            startFragment(currentFragment, currentBundle)
        } else {
            startFragment(SearchFragment::class.java.simpleName)
        }
    }

    fun finishCurrentFragment() {
        activity ?: return

        LoadingState.start()

        when (currentFragment) {
            SignInFragment::class.java.simpleName -> activity?.finish()
            CreateEditFragment::class.java.simpleName -> startFragment(SearchFragment::class.java.simpleName)
            ReadFragment::class.java.simpleName -> startFragment(SearchFragment::class.java.simpleName)
            SearchFragment::class.java.simpleName -> activity?.finish()
            else -> throw RuntimeException("Unknown fragment finish : $currentFragment")
        }
    }

    fun startFragment(fragmentClassName: String, bundle: Bundle? = null) {
        activity ?: return
        currentBundle = bundle

        when (fragmentClassName) {
            SignInFragment::class.java.simpleName -> {
                activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SignInFragment())
                        .commit()
            }
            CreateEditFragment::class.java.simpleName -> {
                with(activity!!.supportFragmentManager) {
                    popBackStack()
                    beginTransaction()
                            .replace(R.id.container, CreateEditFragment().apply { arguments = currentBundle })
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
                with(activity!!.supportFragmentManager) {
                    popBackStack()
                    beginTransaction()
                            .replace(R.id.container, ReadFragment().apply { arguments = currentBundle })
                            .addToBackStack(null)
                            .commit()
                }
            }
            else -> throw RuntimeException("Unknown fragment start : $fragmentClassName")
        }

        currentFragment = fragmentClassName
    }

}