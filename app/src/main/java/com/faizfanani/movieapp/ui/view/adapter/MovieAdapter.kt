package com.faizfanani.movieapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizfanani.movieapp.databinding.ItemMovieBinding
import com.faizfanani.movieapp.interactor.uimodel.Movie

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
class MovieAdapter(
    private val listener: (Int) -> Unit,
) : RecyclerView.Adapter<MovieAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val mDiffer = AsyncListDiffer(this, diffCallback)

    fun addList(newList: List<Movie>) {
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

    class ItemViewHolder private constructor(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: (Int) -> Unit) {
            binding.itemTitle.text = movie.title
            binding.itemRating.text = movie.voteAverage.toString()
            binding.itemReleaseDate.text = movie.releaseDate
            Glide.with(binding.root.context).load(movie.posterPath).into(binding.itemPoster)
            binding.root.setOnClickListener {
                listener(movie.id)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return ItemViewHolder(
                    ItemMovieBinding.inflate(layoutInflater, parent, false)
                )
            }
        }
    }
}