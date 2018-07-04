package ru.aleksandrtrushchinskii.surfproject.ui.search

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.search_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflateBinding
import ru.aleksandrtrushchinskii.surfproject.databinding.SearchFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.ui.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.ViewModelFactory
import javax.inject.Inject


class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: SearchFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        searchViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SearchViewModel::class.java)
        binding = container.inflateBinding(R.layout.search_fragment)
        binding.todoAdapter = TodoAdapter

        searchViewModel.load()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo move this action to viewModel
        fab.setOnClickListener { Navigation.startSearch() }
    }

}
