package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflateBinding
import ru.aleksandrtrushchinskii.surfproject.databinding.SearchFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.ui.adapter.TodoAdapter
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.SearchViewModel
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
        binding.searchViewModel = searchViewModel
        binding.todoAdapter = TodoAdapter

        searchViewModel.load()

        return binding.root
    }

}
