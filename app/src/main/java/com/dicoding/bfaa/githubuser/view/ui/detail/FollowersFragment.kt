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
import com.dicoding.bfaa.githubuser.databinding.FragmentFollowersBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Status
import com.dicoding.bfaa.githubuser.view.adapter.UserAdapter

class FollowersFragment : Fragment() {
    private var binding: FragmentFollowersBinding? = null
    private val detailViewModel: DetailViewModel by activityViewModels()

    private lateinit var userAdapter: UserAdapter

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
            userAdapter = UserAdapter()
            rvFollowers.adapter = userAdapter

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
        detailViewModel.userFollowers.observe(requireActivity(), { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    setLoadingState(false)
                    resource.data?.let { result ->
                        userAdapter.setUsers(result)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private val TAG = FollowersFragment::class.simpleName
    }
}