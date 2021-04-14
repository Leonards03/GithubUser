package com.dicoding.bfaa.githubuser.view.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.githubuser.databinding.FragmentHomeBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Status
import com.dicoding.bfaa.githubuser.view.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
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
                tvSuggestionLabel.invisible()
                tvSuggestionDescription.invisible()
                imgSearch.invisible()
                loading.visible()
            } else {
                loading.invisible()
                rvUsers.visible()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private val TAG = HomeFragment::class.simpleName
    }
}