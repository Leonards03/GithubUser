package com.dicoding.bfaa.githubuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}