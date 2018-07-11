package ru.aleksandrtrushchinskii.surfproject.ui.component

import android.support.v4.app.Fragment
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.ui.MainActivity
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.*


class Navigation constructor(
        activity: MainActivity,
        private val auth: Authentication) {

    private val fm = activity.supportFragmentManager


    fun start() {
        if (auth.isAuth) {
            startFragment(SearchFragment())
        } else {
            startFragment(SignInFragment())
        }
    }

    fun startFragment(fragment: Fragment) = when (fragment) {
        is SignInFragment,
        is SearchFragment -> {
            fm.popBackStack()
            fm.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
        }
        is CreateFragment,
        is ReadFragment,
        is EditFragment -> {
            fm.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
        else -> throw RuntimeException("Unknown fragment start : ${fragment::class}")
    }

}