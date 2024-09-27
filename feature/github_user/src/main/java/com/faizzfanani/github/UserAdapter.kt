package com.faizzfanani.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizzfanani.service_github.domain.model.GithubUser
import id.faizzfanani.technical_test.feature_github_user.databinding.ItemUserBinding

class UserAdapter(
    private val listener: (String) -> Unit,
) : RecyclerView.Adapter<UserAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<GithubUser>() {
        override fun areItemsTheSame(
            oldItem: GithubUser,
            newItem: GithubUser
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GithubUser,
            newItem: GithubUser
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val mDiffer = AsyncListDiffer(this, diffCallback)

    fun addList(newList: List<GithubUser>) {
        mDiffer.submitList(newList.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val detail = mDiffer.currentList[position]
        holder.bind(detail, listener)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    class ItemViewHolder private constructor(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: GithubUser, listener: (String) -> Unit) {
            binding.itemUsername.text = user.username
//            binding.item.text = movie.voteAverage.toString()
//            binding.itemReleaseDate.text = movie.releaseDate
//            Glide.with(binding.root.context).load(movie.posterPath).into(binding.itemPoster)
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