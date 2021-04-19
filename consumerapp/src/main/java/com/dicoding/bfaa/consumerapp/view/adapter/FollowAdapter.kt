package com.dicoding.bfaa.consumerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bfaa.consumerapp.R
import com.dicoding.bfaa.consumerapp.data.model.User
import com.dicoding.bfaa.consumerapp.databinding.ItemRowLayoutBinding

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    private val userList = ArrayList<User>()

    fun setUsers(list: List<User>) {
        with(userList) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowAdapter.FollowViewHolder {
        return FollowViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FollowAdapter.FollowViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRowLayoutBinding by lazy {
            ItemRowLayoutBinding.bind(itemView)
        }

        fun bind(user: User) {
            binding.apply {
                tvUsername.text = user.username
                Glide
                    .with(itemView.context)
                    .load(user.avatar)
                    .into(imgPhoto)
            }
        }
    }
}