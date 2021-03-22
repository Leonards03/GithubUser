package com.dicoding.bfaa.githubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.adapter.UserAdapter
import com.dicoding.bfaa.githubuser.data.UsersData
import com.dicoding.bfaa.githubuser.data.entity.User
import com.dicoding.bfaa.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var usersData: UsersData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            val userAdapter = UserAdapter(getList())
            rvUsers.adapter = userAdapter
        }
    }

    private fun getList(): ArrayList<User> {
        with(resources){
            usersData = UsersData(
                getStringArray(R.array.users_username),
                getStringArray(R.array.users_name),
                obtainTypedArray(R.array.users_avatar),
                getStringArray(R.array.users_location),
                getStringArray(R.array.users_followers),
                getStringArray(R.array.users_following)
            )
        }

        return usersData.list
    }
}