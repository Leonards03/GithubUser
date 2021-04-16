package com.dicoding.bfaa.githubuser.view.ui.home

import android.app.SearchManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.databinding.FragmentHomeBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Status
import com.dicoding.bfaa.githubuser.view.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView()
            setupObservers()
        }
    }

    private fun setupRecyclerView() {
        with(binding!!) {
            rvUsers.layoutManager = LinearLayoutManager(activity)
            userAdapter = UserAdapter()
            rvUsers.adapter = userAdapter

            userAdapter.setItemClickListener(object : UserAdapter.ItemClickListener {
                override fun onItemClicked(username: String) {
                    val toDetailsActivity = HomeFragmentDirections
                        .actionHomeFragmentToDetailActivity(username)
                    findNavController().navigate(toDetailsActivity)
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.query.observe(requireActivity(), { query ->
            searchUser(query)
        })
    }

    private fun searchUser(query: String) {
        viewModel.searchUser(query).observe(requireActivity(), { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    setLoadingState(false)
                    resource.data?.let { result -> userAdapter.setUsers(result) }
                }
                Status.LOADING -> setLoadingState(true)
                Status.ERROR -> Log.e(TAG, resource.message.toString())
            }
        })
    }

    private fun setLoadingState(isDataLoading: Boolean) {
        with(binding!!) {
            if (isDataLoading) {
                emptyState.invisible()
                loading.visible()
            } else {
                loading.invisible()
                rvUsers.visible()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        val searchManager = requireActivity().getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
            queryHint = resources.getString(R.string.hint_search)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (it.isNotEmpty()) viewModel.passQuery(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private val TAG = HomeFragment::class.simpleName
    }
}