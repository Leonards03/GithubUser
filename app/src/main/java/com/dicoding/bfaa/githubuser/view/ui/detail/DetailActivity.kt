package com.dicoding.bfaa.githubuser.view.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.databinding.ActivityDetailBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Status.*
import com.dicoding.bfaa.githubuser.view.adapter.DetailPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailActivityArgs by navArgs()

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        prepareTabLayout()

        val fromNetwork = intent.getBooleanExtra(FROM_NETWORK, true)
        detailViewModel.loadFromNetwork = fromNetwork

        username = getFromIntentOrNavArgs()
        username?.let {
            setActionBarTitle(it)
            setupFavoriteButton(fromNetwork)
            setupObservers(it)
        }
    }

    private fun getFromIntentOrNavArgs(): String {
        return intent.getStringExtra(EXTRA_USERNAME) ?: args.username
    }

    private fun bind(user: User) {
        with(binding) {
            tvFollowers.text = getString(R.string.followers, user.followersCount.toString())
            tvFollowing.text = getString(R.string.following, user.followingCount.toString())
            tvName.text = user.name
            tvUsername.text = user.username

            tvLocation.apply {
                text = user.username
                if (text.isNullOrEmpty())
                    invisible()
                else
                    visible()
            }

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

            Glide
                .with(this@DetailActivity)
                .load(user.avatar)
                .fitCenter()
                .into(imgProfile)

            BottomSheetBehavior.from(btnFavorite).apply {
                state = BottomSheetBehavior.STATE_COLLAPSED
            }

        }
    }

    private fun setupObservers(username: String) {
        detailViewModel.setUsername(username)
        detailViewModel.userDetails.observe(this, { resource ->
            when (resource.status) {
                SUCCESS -> {
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

    private fun setupFavoriteButton(loadFromNetwork: Boolean) {
        if (loadFromNetwork) {
            binding.apply {
                btnFavorite.text = getString(R.string.add_to_favorites)
                btnFavorite.setOnClickListener {
                    detailViewModel.addUserToFavorite()
                    val text = getString(R.string.message_favorite, username)
                    Snackbar.make(
                        this@DetailActivity,
                        btnFavorite,
                        text,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            binding.apply {
                btnFavorite.text = getString(R.string.remove_from_favorites)
                btnFavorite.setOnClickListener {
                    detailViewModel.removeUserFromFavorite()
                    val text = getString(R.string.message_remove_favorite, username)
                    Snackbar.make(
                        this@DetailActivity,
                        btnFavorite,
                        text,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
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
        const val FROM_NETWORK = "load_state"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_repositories,
            R.string.tab_followers,
            R.string.tab_following
        )
        private val TAG = DetailActivity::class.simpleName
    }
}