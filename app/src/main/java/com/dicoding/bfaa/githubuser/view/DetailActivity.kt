package com.dicoding.bfaa.githubuser.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.data.entity.User
import com.dicoding.bfaa.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableBackButton()
        user = intent.getParcelableExtra(EXTRA_USER_DATA)
        user?.let {
            setActionBarTitle(it.username)
            bind(it)
        }
    }

    private fun bind(user: User) {
        with(binding) {
            tvFollowers.text = getString(R.string.followers, user.follower)
            tvFollowing.text = getString(R.string.following, user.following)
            tvName.text = user.name
            tvUsername.text = user.username
            tvLocation.text = user.location
            tvCompany.text = user.company
            tvRepositoryCount.text = user.repository

            Glide
                .with(this@DetailActivity)
                .load(user.avatar)
                .fitCenter()
                .into(imgProfile)

            btnFavorite.setOnClickListener {
                Toast.makeText(
                    this@DetailActivity,
                    getString(R.string.message_favorite, user.username),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setActionBarTitle(title: String) {
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
                val name = user?.name
                val username = user?.username

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.message_share, name, username))
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val EXTRA_USER_DATA = "extra_user_data"
    }
}