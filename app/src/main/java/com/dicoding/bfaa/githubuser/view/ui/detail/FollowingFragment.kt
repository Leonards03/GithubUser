package com.dicoding.bfaa.githubuser.view.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Status.*
import com.dicoding.bfaa.githubuser.view.adapter.UserAdapter

class FollowingFragment : Fragment() {
    private var binding: FragmentFollowingBinding? = null
    private val detailViewModel: DetailViewModel by activityViewModels()

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
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
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            userAdapter = UserAdapter()
            rvFollowing.adapter = userAdapter

            userAdapter.setItemClickListener(object : UserAdapter.ItemClickListener {
                override fun onItemClicked(username: String) {
                    Intent(requireActivity(), DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_USERNAME, username)
                        startActivity(this)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        detailViewModel.userFollowing.observe(requireActivity(), { resource ->
            when (resource.status) {
                SUCCESS -> {
                    setLoadingState(false)
                    resource.data?.let { result ->
                        userAdapter.setUsers(result)
                    }
                }
                LOADING -> {
                    setLoadingState(true)
                }
                ERROR -> {
                    Log.e(TAG, resource.message.toString())
                }
            }
        })
    }

    private fun setLoadingState(isDataLoading: Boolean) {
        binding?.apply {
            if (isDataLoading) {
                loading.visible()
                rvFollowing.invisible()
            } else {
                loading.invisible()
                rvFollowing.visible()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private val TAG = FollowingFragment::class.simpleName
    }
}