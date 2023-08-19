package com.faizfanani.movieapp.ui.view.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun bindViewModel() {
        viewModel.movieID.observe(viewLifecycleOwner) {
            viewModel.fetch()
        }
        viewModel.movieDetail.observe(viewLifecycleOwner) { status ->
            status
                .onLoading {
                    movieDetailVisibility(isLoading = true)
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
                    movieDetailVisibility(isLoading = false)
                }
                .onErrorMessage { _, _ ->
                    dismiss()
                }
        }
        viewModel.reviews.observe(viewLifecycleOwner) { status ->
            status
                .onLoading {
                    reviewVisibility(isLoading = true)
                }
                .onSuccess {
                    if (it.isNotEmpty()) {
                        reviewAdapter.addList(it.distinct())
                        reviewVisibility(isLoading = false)
                    }
                }
                .onErrorMessage { _, _ ->
                    binding.rvReview.visibility = View.GONE
                    binding.labelReview.visibility = View.GONE
                    binding.shimmerMovieReview.visibility = View.VISIBLE
                }
        }
        viewModel.trailerUrl.observe(viewLifecycleOwner) { status ->
            status
                .onLoading {
                    trailerVisibility(isLoading = true)
                }
                .onSuccess {
                    if (it.isNotEmpty()) {
                        trailerVisibility(isLoading = false)
                        binding.videoTrailer.apply {
                            settings.javaScriptEnabled = true
                            webChromeClient = WebChromeClient()
                            val url = "<iframe width=\"100%\" height=\"100%\" src=\"$it\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                            loadData(url, "text/html", "utf-8")
                        }
                    } else {
                        binding.videoTrailer.visibility = View.GONE
                        binding.shimmerMovieTrailer.visibility = View.INVISIBLE
                    }
                }
                .onErrorMessage { _, _ ->
                    binding.videoTrailer.visibility = View.GONE
                    binding.shimmerMovieTrailer.visibility = View.INVISIBLE
                }
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun movieDetailVisibility(isLoading: Boolean) {
        binding.tvRating.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.imgBackdrop.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.btnClose.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvTitle.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.containerPoster.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.labelOverview.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvOverview.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvReleaseDate.visibility = if (isLoading) View.GONE else View.VISIBLE

        binding.shimmerMovieDetail.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun trailerVisibility(isLoading: Boolean) {
        binding.videoTrailer.visibility = if (isLoading) View.GONE else View.VISIBLE

        binding.shimmerMovieTrailer.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun reviewVisibility(isLoading: Boolean) {
        binding.labelReview.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.rvReview.visibility = if (isLoading) View.GONE else View.VISIBLE

        binding.shimmerMovieReview.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}