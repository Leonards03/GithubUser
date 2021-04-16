package com.dicoding.bfaa.githubuser.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.githubuser.R
import com.dicoding.bfaa.githubuser.data.model.Repository
import com.dicoding.bfaa.githubuser.databinding.RepositoryLayoutBinding

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    private val repositories = ArrayList<Repository>()
    private var itemClickListener: ItemClickListener? = null

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setRepositories(list: List<Repository>) {
        with(repositories) {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryAdapter.RepositoryViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.repository_layout, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryAdapter.RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding by lazy {
            RepositoryLayoutBinding.bind(itemView)
        }

        fun bind(repository: Repository) {
            with(binding) {
                tvRepositoryName.text = repository.name
                tvRepositoryLanguage.text = repository.language

                itemView.setOnClickListener {
                    itemClickListener?.onItemClicked(repository.url)
                }
            }
        }
    }

    interface ItemClickListener {
        fun onItemClicked(url: String)
    }
}