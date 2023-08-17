package com.faizfanani.movieapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.faizfanani.movieapp.databinding.ItemGenreBinding
import com.faizfanani.movieapp.interactor.uimodel.Genre

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
class GenreAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<GenreAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem.genreId == newItem.genreId
        }

        override fun areContentsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val mDiffer = AsyncListDiffer(this, diffCallback)

    fun addList(newList: List<Genre>) {
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

    class ItemViewHolder private constructor(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre, listener: (String) -> Unit) {
            binding.genre.text = genre.genreName
            binding.root.setOnClickListener {
                binding.root.isSelected = true
                listener(genre.genreName)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return ItemViewHolder(
                    ItemGenreBinding.inflate(layoutInflater, parent, false)
                )
            }
        }
    }
}