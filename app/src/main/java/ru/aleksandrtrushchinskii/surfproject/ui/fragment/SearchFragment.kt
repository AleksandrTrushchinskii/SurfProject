package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.search_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflateBinding
import ru.aleksandrtrushchinskii.surfproject.common.tools.logDebug
import ru.aleksandrtrushchinskii.surfproject.common.tools.toObservable
import ru.aleksandrtrushchinskii.surfproject.databinding.SearchFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.ui.adapter.TodoAdapter
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.SearchViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: SearchFragmentBinding

    private lateinit var searchDisposable: Disposable


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        searchViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SearchViewModel::class.java)

        binding = container.inflateBinding(R.layout.search_fragment)
        binding.searchViewModel = searchViewModel
        binding.todoAdapter = TodoAdapter

        searchViewModel.load()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView.setQuery(searchViewModel.query.value, false)

        searchViewModel.query.observe(activity!!, Observer {
            logDebug("Query was change $it")
            searchViewModel.load()
        })

        searchDisposable = searchView.toObservable()
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                    logDebug("Searching for $query")
                    searchViewModel.query.value = query
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchDisposable.dispose()
    }

}
