package com.dicoding.bfaa.githubuser.view.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.databinding.FragmentRepositoriesBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Status
import com.dicoding.bfaa.githubuser.view.adapter.RepositoryAdapter

class RepositoriesFragment : Fragment() {
    private var binding: FragmentRepositoriesBinding? = null
    private val detailViewModel: DetailViewModel by activityViewModels()

    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
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
            rvRepositories.layoutManager = LinearLayoutManager(activity)
            repositoryAdapter = RepositoryAdapter()
            rvRepositories.adapter = repositoryAdapter

            repositoryAdapter.setItemClickListener(object: RepositoryAdapter.ItemClickListener{
                override fun onItemClicked(url: String) {
                    Intent(Intent.ACTION_VIEW).apply {
                        data = url.toUri()
                        startActivity(this)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        detailViewModel.fetchUserRepositories().observe(requireActivity(), { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    setLoadingState(false)
                    resource.data?.let { result ->
                        Log.d(TAG, result.toString())
                        repositoryAdapter.setRepositories(result)
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
                rvRepositories.invisible()
            } else {
                loading.invisible()
                rvRepositories.visible()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private val TAG = RepositoriesFragment::class.simpleName
    }
}