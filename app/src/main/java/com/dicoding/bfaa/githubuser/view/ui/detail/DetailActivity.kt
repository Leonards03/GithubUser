package com.dicoding.bfaa.githubuser.view.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.databinding.ActivityDetailBinding
import com.dicoding.bfaa.githubuser.extensions.invisible
import com.dicoding.bfaa.githubuser.extensions.visible
import com.dicoding.bfaa.githubuser.utils.Status.*
import com.dicoding.bfaa.githubuser.view.adapter.DetailPagerAdapter
import com.dicoding.bfaa.githubuser.widget.FavoriteUserAppWidget
import com.dicoding.bfaa.githubuser.widget.FavoriteUserAppWidget.Companion.REFRESH_ACTION
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        prepareTabLayout()

        try {
            prepare(detailViewModel.isFavorite)
        } catch (exception: Exception) {
            prepare(
                intent.getBooleanExtra(IS_FAVORITE, false)
            )
        }
    }

    private fun getFromIntentOrNavArgs(): String {
        return intent.getStringExtra(EXTRA_USERNAME) ?: args.username
    }

    private fun prepare(isFavorite: Boolean) {
        detailViewModel.setFavoriteState(isFavorite)
        setupFavoriteButton(isFavorite)
        val username = getFromIntentOrNavArgs()
        username.let {
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

            setupFavoriteButton(detailViewModel.isFavorite)
            btnFavorite.setOnClickListener {
                onFavoriteButtonClick()
            }

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

    private fun setupFavoriteButton(isFavorite: Boolean) {
        binding.btnFavorite.text = if (isFavorite)
            getString(R.string.remove_from_favorites)
        else
            getString(R.string.add_to_favorites)
    }

    private fun onFavoriteButtonClick() {
        try {
            val text = if (detailViewModel.isFavorite) {
                /*
                 if the present state is favorite,
                 then the action on the button is remove present user from DB
                 */
                detailViewModel.removeUserFromFavorite()
                lifecycleScope.launch {
                    delay(2000L)
                    onBackPressed()
                }
                binding.btnFavorite.isEnabled = false
                getString(R.string.message_remove_favorite, detailViewModel.username)
            } else {
                /*
                else, the button will show up as add user to favorite
                 */
                detailViewModel.addUserToFavorite()
                getString(R.string.message_favorite, detailViewModel.username)
            }
            Snackbar.make(this, binding.btnFavorite, text, Snackbar.LENGTH_SHORT).show()
            detailViewModel.toggleFavorite()
            setupFavoriteButton(detailViewModel.isFavorite)

            // Send Intent to refresh the widgets
            Intent(this@DetailActivity, FavoriteUserAppWidget::class.java).apply {
                action = REFRESH_ACTION
                sendBroadcast(this)
            }
        } catch (exception: Exception) {
            Snackbar.make(
                this,
                binding.btnFavorite,
                getString(R.string.message_error),
                Snackbar.LENGTH_SHORT
            ).show()
            Log.e(TAG, exception.message ?: exception.stackTraceToString())
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
                    putExtra(
                        Intent.EXTRA_TEXT,
                        getString(R.string.message_share, detailViewModel.username)
                    )
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
        const val IS_FAVORITE = "favorite_state"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_repositories,
            R.string.tab_followers,
            R.string.tab_following
        )
        private val TAG = DetailActivity::class.simpleName
    }
}