package com.dicoding.bfaa.githubuser.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.adapter.UserAdapter
import com.dicoding.bfaa.githubuser.data.entity.User
import com.dicoding.bfaa.githubuser.databinding.ActivityMainBinding
import com.dicoding.bfaa.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubUser)
        setContentView(binding.root)

        mainViewModel = MainViewModel(application)

        supportActionBar?.title = "Home"
        with(binding) {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            val userAdapter = UserAdapter(mainViewModel.getUsers())
            rvUsers.adapter = userAdapter

            userAdapter.setOnClickCallback(object : UserAdapter.OnClickCallback {
                override fun onItemClicked(user: User) {
                    toDetailsActivity(user)
                }
            })
        }
    }

    private fun toDetailsActivity(user: User) {
        val detailsIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailsIntent.putExtra(DetailActivity.EXTRA_USER_DATA, user)
        startActivity(detailsIntent)
    }
}