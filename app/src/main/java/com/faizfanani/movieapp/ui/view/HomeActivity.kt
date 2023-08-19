package com.faizfanani.movieapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.faizfanani.movieapp.R
import com.faizfanani.movieapp.databinding.ActivityHomeBinding
import com.faizfanani.movieapp.interactor.util.isLoading
import com.faizfanani.movieapp.interactor.util.onErrorMessage
import com.faizfanani.movieapp.interactor.util.onLoading
import com.faizfanani.movieapp.interactor.util.onSuccess
import com.faizfanani.movieapp.ui.view.adapter.GenreAdapter
import com.faizfanani.movieapp.ui.view.adapter.MovieAdapter
import com.faizfanani.movieapp.ui.view.bottomsheet.MovieDetailBottomSheet
import com.faizfanani.movieapp.ui.view.bottomsheet.NoActionBottomSheet
import com.faizfanani.movieapp.ui.viewmodel.HomeViewModel
import com.faizfanani.movieapp.ui.viewmodel.HomeViewModel.Companion.SIZE
import com.faizfanani.movieapp.utils.BaseActivity
import com.faizfanani.movieapp.utils.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel by viewModels<HomeViewModel>()
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater, null, false) }

    private val genreAdapter
        get() = binding.rvGenre.adapter as GenreAdapter
    private val movieAdapter
        get() = binding.rvMovie.adapter as MovieAdapter
    private val searchMovieAdapter
        get() = binding.rvSearchMovie.adapter as MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun initViews() {
        binding.labelReset.clickWithDebounce {
            viewModel.selectedGenre.postValue(null)
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.selectedGenre.postValue(null)
        }
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    viewModel.viewModelScope.launch {
                        delay(500)
                        viewModel.searchTempList.clear()
                        viewModel.keyword.postValue(newText)
                    }
                } else {
                    refresh()
                }
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })
        binding.rvGenre.apply {
            isNestedScrollingEnabled = true
            adapter = GenreAdapter { genre ->
                actionWithDebounce {
                    viewModel.selectedGenre.postValue(genre)
                }
            }
        }

        binding.rvMovie.apply {
            setHasFixedSize(true)
            adapter = MovieAdapter {
                actionWithDebounce {
                    showMovieDetail(it)
                }
            }
            addOnScrollListener(object : PaginationScrollListener() {
                override fun loadMoreItems() { viewModel.getMovies() }
                override fun getTotalRowCount(): Int = SIZE
                override fun isLastPage(): Boolean = viewModel.isLastPage
                override fun isLoading(): Boolean = viewModel.movie.value.isLoading()
                override fun isTotalRowException(): Boolean = false
            })
        }

        binding.rvSearchMovie.apply {
            setHasFixedSize(true)
            adapter = MovieAdapter {
                actionWithDebounce {
                    showMovieDetail(it)
                }
            }
            addOnScrollListener(object : PaginationScrollListener() {
                override fun loadMoreItems() { viewModel.searchMovies() }
                override fun getTotalRowCount(): Int = SIZE
                override fun isLastPage(): Boolean = viewModel.isLastPage
                override fun isLoading(): Boolean = viewModel.searchResultMovie.value.isLoading()
                override fun isTotalRowException(): Boolean = false
            })
        }
    }
    private fun bindViewModel() {
        viewModel.genre.observe(this) { status ->
            status
                .onLoading {
                    binding.shimmerGenre.visibility = View.VISIBLE
                    binding.rvGenre.visibility = View.INVISIBLE
                }
                .onSuccess {
                    genreAdapter.addList(it.distinct())
                    binding.shimmerGenre.visibility = View.GONE
                    binding.rvGenre.visibility = View.VISIBLE
                }
                .onErrorMessage { message, _ ->
                    binding.shimmerGenre.visibility = View.GONE
                    binding.rvGenre.visibility = View.GONE
                    showMessage(getString(R.string.data_is_empty), message)
                }
        }

        viewModel.movie.observe(this) { status ->
            status
                .onLoading {
                    binding.shimmerMovie.visibility = View.VISIBLE
                    binding.rvSearchMovie.visibility = View.GONE
                    binding.rvMovie.visibility = View.GONE
                }
                .onSuccess {
                    movieAdapter.addList(it.distinct())
                    binding.shimmerMovie.visibility = View.GONE
                    binding.rvSearchMovie.visibility = View.GONE
                    binding.rvMovie.visibility = View.VISIBLE
                    binding.swipeRefresh.isRefreshing = false
                }
                .onErrorMessage { message, _ ->
                    binding.shimmerMovie.visibility = View.GONE
                    binding.rvMovie.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    showMessage(getString(R.string.data_is_empty), message)
                }
        }

        viewModel.searchResultMovie.observe(this) { status ->
            status
                .onLoading {
                    binding.shimmerMovie.visibility = View.VISIBLE
                    binding.rvMovie.visibility = View.GONE
                    binding.rvSearchMovie.visibility = View.GONE
                    binding.labelDiscover.visibility = View.GONE
                    binding.labelGenre.visibility = View.GONE
                    binding.rvGenre.visibility = View.GONE
                }
                .onSuccess {
                    searchMovieAdapter.addList(it.distinct())
                    binding.shimmerMovie.visibility = View.GONE
                    binding.rvMovie.visibility = View.GONE
                    binding.rvSearchMovie.visibility = View.VISIBLE
                }
                .onErrorMessage { message, _ ->
                    binding.shimmerMovie.visibility = View.GONE
                    binding.rvSearchMovie.visibility = View.GONE
                    showMessage(getString(R.string.data_is_empty), message)
                }
        }

        viewModel.selectedGenre.observe(this) {
            if (it != null) {
                refresh()
                binding.labelDiscover.text = getString(R.string.label_selected_genre, it.genreName)
                binding.labelReset.visibility = View.VISIBLE
            } else {
                refresh()
                binding.labelDiscover.text = getString(R.string.label_discover)
                binding.labelReset.visibility = View.GONE
            }
        }
        viewModel.keyword.observe(this) {
            if (it.isNotEmpty()) {
                viewModel.searchMovies()
                binding.rvSearchMovie.smoothScrollToPosition(0)
            }
        }
    }

    private fun refresh() {
        viewModel.refresh()
        binding.labelDiscover.visibility = View.VISIBLE
        binding.labelGenre.visibility = View.VISIBLE
        binding.rvMovie.smoothScrollToPosition(0)
    }

    private fun showMessage(title: String, message: String?) {
        NoActionBottomSheet.newInstance(
            title = title,
            desc = message,
            autoDismiss = 3000,
        ).show(supportFragmentManager, "Show message")
    }

    private fun showMovieDetail(movieID: Int) {
        MovieDetailBottomSheet.newInstance(
            movieID = movieID
        ).show(supportFragmentManager, "Show movie detail")
    }
}