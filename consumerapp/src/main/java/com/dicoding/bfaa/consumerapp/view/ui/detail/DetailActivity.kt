package com.dicoding.bfaa.consumerapp.view.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.bfaa.consumerapp.R
import com.dicoding.bfaa.consumerapp.data.model.User
import com.dicoding.bfaa.consumerapp.databinding.ActivityDetailBinding
import com.dicoding.bfaa.consumerapp.extensions.invisible
import com.dicoding.bfaa.consumerapp.extensions.visible
import com.dicoding.bfaa.consumerapp.utils.Status.*
import com.dicoding.bfaa.consumerapp.view.adapter.DetailPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailViewModel by viewModels()

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        prepareTabLayout()

        username = intent.getStringExtra(EXTRA_USERNAME)
        username?.let {
            setActionBarTitle(it)
            setupObservers(it)
        }
    }

    private fun bind(user: User) {
        with(binding) {
            tvFollowers.text = getString(R.string.followers, user.followersCount.toString())
            tvFollowing.text = getString(R.string.following, user.followingCount.toString())
            tvName.text = user.name
            tvUsername.text = user.username

            tvCompany.apply {
                text = user.company
                if (text.isNullOrEmpty())
                    invisible()
                else
                    visible()
            }

            tvLocation.apply {
                text = user.location
                if (text.isNullOrEmpty())
                    invisible()
                else
                    visible()
            }

            tvBio.apply {
                text = user.bio
                if (text.isNullOrEmpty())
                    invisible()
                else
                    visible()
            }

            Glide
                .with(this@DetailActivity)
                .load(user.avatar)
                .fitCenter()
                .into(imgProfile)

            btnFavorite.setOnClickListener {
                viewModel.removeUserFromFavorite(username ?: String(), this@DetailActivity)
                val text = getString(R.string.remove_user_from_favorite, username)
                Snackbar.make(this@DetailActivity, btnFavorite, text, Snackbar.LENGTH_SHORT)
                    .show()
                btnFavorite.isEnabled = false
            }

            BottomSheetBehavior.from(btnFavorite).apply {
                state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setupObservers(username: String) {
        viewModel.prepare(username, this@DetailActivity)
        viewModel.userDetails.observe(this, { resource ->
            when (resource.status) {
                SUCCESS -> {
                    binding.btnFavorite.isEnabled = true
                    resource.data?.let { result ->
                        bind(result)
                    }
                }
                LOADING -> {
                }
                ERROR -> Log.e(TAG, resource.message.toString())
            }
        })
    }


    private fun prepareTabLayout() {
        with(binding) {
            viewPager.adapter = DetailPagerAdapter(this@DetailActivity)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    private fun setActionBarTitle(title: String) {
        enableBackButton()
        supportActionBar?.title = title
    }

    private fun enableBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_menu_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.message_share, username))
                    type = "text/plain"
                }

                Intent.createChooser(sendIntent, null).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_repositories,
            R.string.tab_followers,
            R.string.tab_following
        )
        private val TAG = DetailActivity::class.simpleName
    }
}
