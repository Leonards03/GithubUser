package com.dicoding.bfaa.githubuser.view.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.databinding.FragmentFavoriteBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Resource
import com.dicoding.bfaa.githubuser.utils.Status
import com.dicoding.bfaa.githubuser.view.adapter.UserAdapter
import com.dicoding.bfaa.githubuser.view.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var binding: FragmentFavoriteBinding? = null

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
            rvUsers.layoutManager = LinearLayoutManager(activity)
            userAdapter = UserAdapter()
            rvUsers.adapter = userAdapter

            userAdapter.setItemClickListener(object : UserAdapter.ItemClickListener {
                override fun onItemClicked(username: String) {
                    Intent(requireActivity(), DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_USERNAME, username)
                        putExtra(DetailActivity.IS_FAVORITE, true)
                        startActivity(this)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.favoriteUser.observe(requireActivity(), ::observeFavoriteUserList)
    }

    private fun observeFavoriteUserList(resource: Resource<List<User>>){
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let { result ->
                    setLoadingState(false)
                    userAdapter.setUsers(result)
                    showEmptyState()
                }
            }
            Status.LOADING -> setLoadingState(true)
            Status.ERROR -> Log.e(TAG, resource.message.toString())
        }
    }

    private fun showEmptyState() {
        Log.d(TAG, userAdapter.itemCount.toString())
        if (userAdapter.itemCount == 0) {
            binding?.emptyState?.visible()
        }
    }

    private fun setLoadingState(isDataLoading: Boolean) {
        binding?.apply {
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

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            viewModel.loadList()
        }
        userAdapter.notifyDataSetChanged()
    }

    companion object {
        private val TAG = FavoriteFragment::class.simpleName
    }
}