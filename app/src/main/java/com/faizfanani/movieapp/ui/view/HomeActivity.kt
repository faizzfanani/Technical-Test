package com.faizfanani.movieapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.faizfanani.movieapp.R
import com.faizfanani.movieapp.databinding.ActivityHomeBinding
import com.faizfanani.movieapp.interactor.util.isLoading
import com.faizfanani.movieapp.interactor.util.onErrorMessage
import com.faizfanani.movieapp.interactor.util.onLoading
import com.faizfanani.movieapp.interactor.util.onSuccess
import com.faizfanani.movieapp.ui.view.adapter.GenreAdapter
import com.faizfanani.movieapp.ui.view.adapter.MovieAdapter
import com.faizfanani.movieapp.ui.viewmodel.HomeViewModel
import com.faizfanani.movieapp.ui.viewmodel.HomeViewModel.Companion.SIZE
import com.faizfanani.movieapp.utils.BaseActivity
import com.faizfanani.movieapp.utils.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel by viewModels<HomeViewModel>()
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater, null, false) }

    private val genreAdapter
        get() = binding.rvGenre.adapter as GenreAdapter
    private val movieAdapter
        get() = binding.rvMovie.adapter as MovieAdapter

//    companion object {
//        private const val REQUEST_SUCCESS = "com.faizfanani.movieapp.ui.view.HomeActivity.REQUEST_SUCCESS"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //callbackListener()
        initViews()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

//    private fun callbackListener() {
//        supportFragmentManager.setFragmentResultListener(
//            REQUEST_SUCCESS,
//            this
//        ) { _, bundle ->
//            if (bundle.getString(Constants.ACTION_RESULT) == Constants.RESULT_DISMISS) {
//                val intent = Intent(applicationContext, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }
    private fun initViews() {
        binding.rvGenre.apply {
            isNestedScrollingEnabled = true
            adapter = GenreAdapter { genre ->
                actionWithDebounce {
                    viewModel.genreName.postValue(genre)
                }
            }
        }

        binding.rvMovie.apply {
            isNestedScrollingEnabled = false
            adapter = MovieAdapter { movieId ->
                actionWithDebounce {

                }
            }
            addOnScrollListener(object : PaginationScrollListener() {
                override fun loadMoreItems() { viewModel.getMovies() }
                override fun getTotalRowCount(): Int = SIZE/2
                override fun isLastPage(): Boolean = viewModel.isLastPage
                override fun isLoading(): Boolean = viewModel.movie.value.isLoading()
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
                    genreAdapter.addList(it)
                    binding.shimmerGenre.visibility = View.GONE
                    binding.rvGenre.visibility = View.VISIBLE
                }
                .onErrorMessage { message, _ ->
                    binding.shimmerGenre.visibility = View.GONE
                    binding.rvGenre.visibility = View.GONE
                    showMessage(getString(R.string.data_is_empty), message, null)
                }
        }

        viewModel.movie.observe(this) { status ->
            status
                .onLoading {
                    binding.shimmerMovie.visibility = View.VISIBLE
                    binding.rvMovie.visibility = View.GONE
                }
                .onSuccess {
                    movieAdapter.addList(it)
                    binding.shimmerMovie.visibility = View.GONE
                    binding.rvMovie.visibility = View.VISIBLE
                }
                .onErrorMessage { message, _ ->
                    binding.shimmerMovie.visibility = View.GONE
                    binding.rvMovie.visibility = View.GONE
                    showMessage(getString(R.string.data_is_empty), message, null)
                }
        }

        viewModel.genreName.observe(this) {
            viewModel.refresh()
        }
    }

//    private fun validateInput(): Boolean {
//        return if (binding.etCredentials.text.isNullOrEmpty()){
//            binding.tilCredentials.error = "Harap isi email terlebih dahulu"
//            false
//        } else if (binding.etPassword.text.isNullOrEmpty()){
//            binding.tilPassword.error = "Harap isi password terlebih dahulu"
//            false
//        } else {
//            viewModel.email = binding.etCredentials.text.toString()
//            viewModel.password = binding.etPassword.text.toString()
//            true
//        }
//    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun showMessage(title: String, message: String?, requestKey: String?) {
//        NoActionBottomSheet.newInstance(
//            title = title,
//            desc = message,
//            autoDismiss = 3000,
//            requestKey = requestKey
//        ).show(supportFragmentManager, "Login")
    }
}