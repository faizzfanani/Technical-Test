package com.faizfanani.movieapp.ui.view.bottomsheet

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faizfanani.movieapp.databinding.BottomSheetMovieDetailBinding
import com.faizfanani.movieapp.interactor.util.onErrorMessage
import com.faizfanani.movieapp.interactor.util.onLoading
import com.faizfanani.movieapp.interactor.util.onSuccess
import com.faizfanani.movieapp.ui.view.adapter.GenreAdapter
import com.faizfanani.movieapp.ui.view.adapter.ReviewAdapter
import com.faizfanani.movieapp.ui.viewmodel.MovieDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailBottomSheet : BottomSheetDialogFragment() {

    private val viewModel by viewModels<MovieDetailViewModel>()
    private var mBinding: BottomSheetMovieDetailBinding? = null
    private val binding
        get() = mBinding!!
    private val genreAdapter
        get() = binding.rvGenre.adapter as GenreAdapter
    private val reviewAdapter
        get() = binding.rvReview.adapter as ReviewAdapter

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        fun newInstance(
            movieID: Int,
        ): MovieDetailBottomSheet {
            return MovieDetailBottomSheet().apply {
                arguments = bundleOf(
                    MOVIE_ID to movieID,
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
        viewModel.movieID.postValue(arguments?.getInt(MOVIE_ID))

        initViews()
        bindViewModel()

        return binding.root
    }

    private fun initViews() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.rvGenre.apply {
            isNestedScrollingEnabled = true
            isEnabled = false
            adapter = GenreAdapter {}
        }
        binding.rvReview.apply {
            isNestedScrollingEnabled = false
            adapter = ReviewAdapter()
        }
    }

    private fun bindViewModel() {
        viewModel.movieID.observe(viewLifecycleOwner) {
            viewModel.fetch()
        }
        viewModel.movieDetail.observe(viewLifecycleOwner) { status ->
            status
                .onLoading {

                }
                .onSuccess {
                    binding.tvTitle.text = it.title
                    binding.tvOverview.text = it.overview
                    binding.tvReleaseDate.text = it.releaseDate
                    binding.tvRating.text = it.voteAverage.toString()
                    if (it.genres.isNotEmpty()) {
                        genreAdapter.addList(it.genres.distinct())
                        binding.rvGenre.visibility = View.VISIBLE
                    }
                    Glide.with(this).load(it.backdropPath).into(binding.imgBackdrop)
                    Glide.with(this).load(it.posterPath).into(binding.imgPoster)
                }
                .onErrorMessage { _, _ ->
                    dismiss()
                }
        }
        viewModel.reviews.observe(viewLifecycleOwner) { status ->
            status
                .onLoading {

                }
                .onSuccess {
                    if (it.isNotEmpty()) {
                        reviewAdapter.addList(it.distinct())
                        binding.rvGenre.visibility = View.VISIBLE
                    }
                }
                .onErrorMessage { _, _ ->
                    binding.rvReview.visibility = View.GONE
                    binding.labelReview.visibility = View.GONE
                }
        }
        viewModel.trailerUrl.observe(viewLifecycleOwner) { status ->
            status
                .onLoading {
                    binding.videoTrailer.visibility = View.GONE
                }
                .onSuccess {
                    if (it.isNotEmpty()) {
                        binding.videoTrailer.setVideoURI(Uri.parse(it))
                        binding.videoTrailer.setMediaController(MediaController(context))
                        binding.videoTrailer.requestFocus()
                        binding.videoTrailer.start()
                        binding.videoTrailer.visibility = View.VISIBLE
                    } else
                        binding.videoTrailer.visibility = View.GONE
                }
                .onErrorMessage { _, _ ->
                    binding.videoTrailer.visibility = View.GONE
                }
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}