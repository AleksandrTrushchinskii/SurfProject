package ru.aleksandrtrushchinskii.surfproject.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.main_activity.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.ui.search.SearchFragment

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, SearchFragment())
                .commit()

    }

}
