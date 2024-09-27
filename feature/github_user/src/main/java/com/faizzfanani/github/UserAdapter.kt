package com.faizzfanani.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizzfanani.service_github.domain.model.GithubUser
import id.faizzfanani.technical_test.feature_github_user.databinding.ItemUserBinding

class UserAdapter(
    private val listener: (String) -> Unit,
) : RecyclerView.Adapter<UserAdapter.ItemViewHolder>() {

    private var userList = mutableListOf<GithubUser>()
    fun addList(newList: List<GithubUser>) {
        userList.addAll(newList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val detail = userList[position]
        holder.bind(detail, listener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ItemViewHolder private constructor(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: GithubUser, listener: (String) -> Unit) {
            binding.itemUsername.text = user.username
            binding.itemEmail.text = user.email
            Glide.with(binding.root.context).load(user.avatarUrl).into(binding.avatar)
            binding.root.setOnClickListener {
                listener(user.username)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return ItemViewHolder(
                    ItemUserBinding.inflate(layoutInflater, parent, false)
                )
            }
        }
    }
}