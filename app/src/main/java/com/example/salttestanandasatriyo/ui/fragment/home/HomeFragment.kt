package com.example.salttestanandasatriyo.ui.fragment.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.salttestanandasatriyo.R
import com.example.salttestanandasatriyo.common.Resource
import com.example.salttestanandasatriyo.common.gone
import com.example.salttestanandasatriyo.common.searchQuery
import com.example.salttestanandasatriyo.common.show
import com.example.salttestanandasatriyo.data.model.Article
import com.example.salttestanandasatriyo.ui.NewsViewModel
import com.example.salttestanandasatriyo.ui.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), NewsAdapter.Interaction,
    SearchView.OnQueryTextListener {

    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter by lazy { NewsAdapter(this) }
    private lateinit var responseList: MutableList<Article>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        responseList = mutableListOf()
        viewModel.getHomeNews(false)

        setupRecyclerView()
        observeToNewsLiveData()
        observeToErrorLiveData(view)

    }

    private fun observeToErrorLiveData(view: View) {


        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.error.postValue(false)
                Snackbar.make(
                    view,
                    ("No Data Saved "),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(("retry")) {
                        viewModel.getHomeNews(true)
                    }
                    .show()
            }
        })
    }


    private fun observeToNewsLiveData() {
        viewModel.getNews().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Error -> {
                    ProgressBar.gone()
                }

                is Resource.Loading -> ProgressBar.show()
                is Resource.Success -> {

                    if (it.data != null) {
                        responseList.clear()
                        ProgressBar.gone()
                        swipeRefresh.isRefreshing = false
                        newsAdapter.differ.submitList(it.data)
                        responseList.addAll(it.data)  // add the call from api to list in memory to search
                    }
                }
            }
        })

    }


    private fun setupRecyclerView() {
        swipeRefresh.apply {
            setOnRefreshListener {
                responseList.clear()
                viewModel.getHomeNews(true)
                observeToNewsLiveData()

            }
        }

        newsRecycler.apply {
            adapter = newsAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Article) {
        val action = HomeFragmentDirections.actionNavExploreToDetailsFragment(item)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        onQueryTextChange(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newsAdapter.differ.submitList(searchQuery(newText, responseList))
        return true
    }

}
