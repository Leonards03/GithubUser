package com.dicoding.bfaa.consumerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bfaa.consumerapp.R
import com.dicoding.bfaa.consumerapp.data.model.User
import com.dicoding.bfaa.consumerapp.databinding.UserRowLayoutBinding
import com.dicoding.bfaa.consumerapp.extensions.invisible
import com.dicoding.bfaa.consumerapp.extensions.visible

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val userList = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setUsers(list: List<User>) {
        with(userList) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder =
        UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_row_layout, parent, false)
        )

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding by lazy {
            UserRowLayoutBinding.bind(itemView)
        }

        fun bind(user: User) {
            binding.apply {
                Glide
                    .with(itemView.context)
                    .load(user.avatar)
                    .into(imgPhoto)

                tvName.text = user.name
                tvUsername.text = user.username
                tvWork.apply {
                    text = user.company
                    if (text.isNullOrEmpty())
                        invisible()
                    else
                        visible()
                }

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}