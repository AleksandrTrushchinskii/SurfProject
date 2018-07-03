package ru.aleksandrtrushchinskii.surfproject.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.support.DaggerAppCompatActivity
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.common.tools.KEY_CURRENT_FRAGMENT
import ru.aleksandrtrushchinskii.surfproject.ui.create.CreateFragment
import ru.aleksandrtrushchinskii.surfproject.ui.search.SearchFragment
import ru.aleksandrtrushchinskii.surfproject.ui.signin.SignInFragment
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var currentFragment: String

    @Inject
    lateinit var auth: Authentication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        currentFragment = savedInstanceState?.getString(KEY_CURRENT_FRAGMENT) ?: ""

        if (!auth.isAuth) {
            startFragment(SignInFragment::class.java.simpleName)
        } else {
            if (currentFragment.isNotEmpty()) {
                startFragment(currentFragment)
            } else {
                startFragment(SearchFragment::class.java.simpleName)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(KEY_CURRENT_FRAGMENT, currentFragment)
    }

    fun startFragment(fragmentClassName: String) {
        when (fragmentClassName) {
            SignInFragment::class.java.simpleName -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SignInFragment())
                        .commit()
            }
            CreateFragment::class.java.simpleName -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CreateFragment())
                        .addToBackStack(null)
                        .commit()
            }
            SearchFragment::class.java.simpleName -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment())
                        .commit()
            }
            else -> throw RuntimeException("Unknown fragment start : $fragmentClassName")
        }
    }

    fun finishFragment(fragment: Fragment) = when (fragment::class.java) {
        SignInFragment::class.java -> startFragment(SearchFragment::class.java.simpleName)
        else -> throw RuntimeException("Unknown fragment finish : ${fragment::class.java.simpleName}")
    }

}
