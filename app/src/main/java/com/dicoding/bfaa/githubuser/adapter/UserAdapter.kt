package com.dicoding.bfaa.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.data.entity.User
import com.dicoding.bfaa.githubuser.databinding.ItemRowLayoutBinding

class UserAdapter(private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onClickCallback: OnClickCallback

    fun setOnClickCallback(onClickCallback: OnClickCallback) {
        this.onClickCallback = onClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding by lazy {
            ItemRowLayoutBinding.bind(itemView)
        }

        fun bind(user: User) {
            with(binding) {
                Glide
                    .with(itemView.context)
                    .load(user.avatar)
                    .into(imgPhoto)
                tvName.text = user.name
                tvUsername.text = user.username

                itemView.setOnClickListener {
                    onClickCallback.onItemClicked(user)
                }
            }
        }
    }

    interface OnClickCallback {
        fun onItemClicked(user: User)
    }
}