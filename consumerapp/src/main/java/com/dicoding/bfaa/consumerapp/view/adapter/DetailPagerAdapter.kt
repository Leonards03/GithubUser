package com.dicoding.bfaa.consumerapp.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.bfaa.consumerapp.view.ui.detail.FollowersFragment
import com.dicoding.bfaa.consumerapp.view.ui.detail.FollowingFragment
import com.dicoding.bfaa.consumerapp.view.ui.detail.RepositoriesFragment

class DetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RepositoriesFragment()
            1 -> FollowersFragment()
            else -> FollowingFragment()
        }
    }
}