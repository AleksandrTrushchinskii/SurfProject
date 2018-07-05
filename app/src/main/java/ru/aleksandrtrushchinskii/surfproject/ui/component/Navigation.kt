package ru.aleksandrtrushchinskii.surfproject.ui.component

import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.ui.MainActivity
import ru.aleksandrtrushchinskii.surfproject.ui.create.CreateFragment
import ru.aleksandrtrushchinskii.surfproject.ui.search.SearchFragment
import ru.aleksandrtrushchinskii.surfproject.ui.signin.SignInFragment


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
            else -> throw RuntimeException("Unknown fragment finish : $currentFragment")
        }
    }

    fun startFragment(fragmentClassName: String) {
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
            else -> throw RuntimeException("Unknown fragment start : $fragmentClassName")
        }

        currentFragment = fragmentClassName
    }

}