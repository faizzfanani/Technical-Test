package com.faizfanani.movieapp.ui.view.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.faizfanani.movieapp.databinding.BottomSheetMovieDetailBinding
import com.faizfanani.movieapp.interactor.uimodel.Movie
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MovieDetailBottomSheet : BottomSheetDialogFragment() {
    private var mBinding: BottomSheetMovieDetailBinding? = null
    private val binding
        get() = mBinding!!
    private var movie: Movie? = null

    companion object {
        private const val MOVIE = "MOVIE"
        fun newInstance(
            movie: Movie,
        ): MovieDetailBottomSheet {
            return MovieDetailBottomSheet().apply {
                arguments = bundleOf(
                    MOVIE to movie,
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = BottomSheetMovieDetailBinding.inflate(inflater, container, false)
        movie = arguments?.getBundle(MOVIE) as Movie?

        initViews()

        return binding.root
    }

    private fun initViews() {
        movie?.let {
            binding.tvTitle.text = it.title
            binding.tvOverview.text = it.overview
            binding.tvReleaseDate.text = it.releaseDate
            Glide.with(this).load(it.backdropPath).into(binding.imgBackdrop)
            Glide.with(this).load(it.posterPath).into(binding.imgPoster)
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}