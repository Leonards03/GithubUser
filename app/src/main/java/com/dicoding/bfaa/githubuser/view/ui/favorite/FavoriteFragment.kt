package com.dicoding.bfaa.githubuser.view.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.githubuser.databinding.FragmentFavoriteBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Status
import com.dicoding.bfaa.githubuser.view.adapter.UserAdapter
import com.dicoding.bfaa.githubuser.view.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var binding: FragmentFavoriteBinding? = null

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
                    Intent(requireActivity(), DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_USERNAME, username)
                        putExtra(DetailActivity.FROM_NETWORK, false)
                        startActivity(this)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.favoriteUser.observe(requireActivity(), { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { result ->
                        setLoadingState(false)
                        userAdapter.setUsers(result)
                    }
                }
                Status.LOADING -> setLoadingState(true)
                Status.ERROR -> Log.e(TAG, resource.message.toString())
            }
        })
    }

    private fun setLoadingState(isDataLoading: Boolean) {
        with(binding!!) {
            if (isDataLoading) {
                loading.visible()
                rvUsers.invisible()
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
        private val TAG = FavoriteFragment::class.simpleName
    }
}