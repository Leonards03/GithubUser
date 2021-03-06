package com.dicoding.bfaa.githubuser.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.data.model.User
import com.dicoding.bfaa.githubuser.databinding.ItemRowLayoutBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val userList = ArrayList<User>()
    private var itemClickListener: ItemClickListener? = null

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setUsers(list: List<User>) {
        with(userList) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun clearList(){
        userList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.count()

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
                tvUsername.text = user.username

                itemView.setOnClickListener {
                    itemClickListener?.onItemClicked(user.username)
                }
            }
        }
    }

    interface ItemClickListener {
        fun onItemClicked(username: String)
    }
}