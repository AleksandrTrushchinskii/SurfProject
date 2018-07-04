package ru.aleksandrtrushchinskii.surfproject.ui

import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.ui.create.CreateFragment
import ru.aleksandrtrushchinskii.surfproject.ui.search.SearchFragment
import ru.aleksandrtrushchinskii.surfproject.ui.signin.SignInFragment


object Navigation {

    private var activity: MainActivity? = null
    private var currentFragment: String = ""


    fun init(activity: MainActivity) {
        this.activity = activity
    }

    fun clear() {
        activity = null
    }

    fun startSignIn() {
        activity ?: return

        startFragment(SignInFragment::class.java.simpleName)
    }

    fun startSearch() {
        activity ?: return

        startFragment(CreateFragment::class.java.simpleName)
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

        when (currentFragment) {
            SignInFragment::class.java.simpleName -> startFragment(SearchFragment::class.java.simpleName)
            CreateFragment::class.java.simpleName -> startFragment(SearchFragment::class.java.simpleName)
            else -> throw RuntimeException("Unknown fragment finish : ${currentFragment}")
        }
    }

    private fun startFragment(fragmentClassName: String) {
        activity ?: return

        currentFragment = fragmentClassName

        when (fragmentClassName) {
            SignInFragment::class.java.simpleName -> {
                activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SignInFragment())
                        .commit()
            }
            CreateFragment::class.java.simpleName -> {
                activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CreateFragment())
                        .addToBackStack(null)
                        .commit()
            }
            SearchFragment::class.java.simpleName -> {
                activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment())
                        .commit()
            }
            else -> throw RuntimeException("Unknown fragment start : $fragmentClassName")
        }
    }

}