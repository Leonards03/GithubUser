package com.dicoding.bfaa.consumerapp.view.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.consumerapp.databinding.FragmentFollowersBinding
import com.dicoding.bfaa.consumerapp.extensions.invisible
import com.dicoding.bfaa.consumerapp.extensions.visible
import com.dicoding.bfaa.consumerapp.utils.Status
import com.dicoding.bfaa.consumerapp.view.adapter.FollowAdapter


class FollowersFragment : Fragment() {
    private var binding: FragmentFollowersBinding? = null

    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var userAdapter: FollowAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView()
            setupObservers()
        }
    }


    private fun setupRecyclerView() {
        binding?.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            userAdapter = FollowAdapter()
            rvFollowers.adapter = userAdapter
        }
    }

    private fun setupObservers() {
        viewModel.userFollowers.observe(requireActivity(), { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    setLoadingState(false)
                    resource.data?.let { result ->
                        userAdapter.setUsers(result)
                        Log.d(TAG, result.toString())
                    }
                }
                Status.LOADING -> setLoadingState(true)
                Status.ERROR -> Log.e(TAG, resource.message.toString())
            }
        })
    }


    private fun setLoadingState(isDataLoading: Boolean) {
        binding?.apply {
            if (isDataLoading) {
                loading.visible()
                rvFollowers.invisible()
            } else {
                loading.invisible()
                rvFollowers.visible()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private val TAG = FollowersFragment::class.simpleName
    }
}