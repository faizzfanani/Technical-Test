package com.faizfanani.movieapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizfanani.movieapp.R
import com.faizfanani.movieapp.databinding.ItemReviewBinding
import com.faizfanani.movieapp.interactor.uimodel.Review

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(
            oldItem: Review,
            newItem: Review
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Review,
            newItem: Review
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val mDiffer = AsyncListDiffer(this, diffCallback)

    fun addList(newList: List<Review>) {
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
        holder.bind(detail)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    class ItemViewHolder private constructor(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            review.author?.let {
                binding.tvAuthorName.text = it.name.ifEmpty { it.username }
                binding.tvAuthorRating.text = binding.root.context.getString(R.string.label_rating, it.rating.toString())
                if (it.avatarPath.isNotEmpty())
                    Glide.with(binding.root.context).load(it.avatarPath).into(binding.imgAvatar)
            }
            binding.tvAuthorContent.text = review.content
            binding.tvAuthorDate.text = review.createdAt
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return ItemViewHolder(
                    ItemReviewBinding.inflate(layoutInflater, parent, false)
                )
            }
        }
    }
}