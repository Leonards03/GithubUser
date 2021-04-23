package com.dicoding.bfaa.consumerapp.view.ui.home

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.consumerapp.data.local.provider.ContentProviderConstants.CONTENT_URI
import com.dicoding.bfaa.consumerapp.data.model.User
import com.dicoding.bfaa.consumerapp.databinding.ActivityHomeBinding
import com.dicoding.bfaa.consumerapp.di.IoDispatcher
import com.dicoding.bfaa.consumerapp.extensions.invisible
import com.dicoding.bfaa.consumerapp.extensions.visible
import com.dicoding.bfaa.consumerapp.utils.Status.*
import com.dicoding.bfaa.consumerapp.view.adapter.UserAdapter
import com.dicoding.bfaa.consumerapp.view.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@HomeActivity)
            rvUsers.adapter = userAdapter

            userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    Intent(this@HomeActivity, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                        startActivity(this)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.favoriteList.observe(this, { resource ->
            when (resource.status) {
                SUCCESS -> {
                    resource.data?.let { data ->
                        setLoadingState(false)
                        userAdapter.setUsers(data)
                        showEmptyState()
                    }
                }
                LOADING -> setLoadingState(true)
                ERROR -> Log.e(TAG, resource.message ?: "Error Occured")
            }
        })

        lifecycleScope.launch(ioDispatcher) {
            viewModel.getFavoriteList(this@HomeActivity)
        }

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                CoroutineScope(ioDispatcher).launch {
                    viewModel.getFavoriteList(this@HomeActivity)
                }
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    private fun showEmptyState() {
        if (userAdapter.itemCount == 0) {
            binding.emptyState.visible()
        }
    }

    private fun setLoadingState(isDataLoading: Boolean) {
        binding.apply {
            if (isDataLoading) {
                loading.visible()
                rvUsers.invisible()
            } else {
                loading.invisible()
                rvUsers.visible()
            }
        }
    }

    companion object {
        private val TAG = HomeActivity::class.simpleName
    }
}