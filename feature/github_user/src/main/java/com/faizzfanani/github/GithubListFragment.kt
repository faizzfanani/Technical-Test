package com.faizzfanani.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.faizzfanani.core.base.BaseFragment
import com.faizzfanani.core.domain.util.ConnectivityListener
import com.faizzfanani.core_ui.component.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import id.faizzfanani.technical_test.feature_github_user.databinding.FragmentGithubListBinding
import timber.log.Timber

@AndroidEntryPoint
class GithubListFragment : BaseFragment<FragmentGithubListBinding>(), ConnectivityListener {

    private val viewModel by viewModels<GithubListViewModel>()

    private var username = ""
    private val userAdapter
        get() = viewBinding.rvUser.adapter as UserAdapter

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentGithubListBinding {
        return FragmentGithubListBinding.inflate(layoutInflater, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.connectivityListener = this

        initViews()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initViews(){
        viewBinding.swipeRefresh.setOnRefreshListener {
            viewModel.getGithubUser(username)
        }

        viewBinding.rvUser.apply {
            setHasFixedSize(true)
            adapter = UserAdapter {

            }
            addOnScrollListener(object : PaginationScrollListener() {
                override fun loadMoreItems() { viewModel.getGithubUser(username) }
                override fun getTotalRowCount(): Int = viewModel.pageSize
                override fun isLastPage(): Boolean = viewModel.isLastPage
                override fun isLoading(): Boolean = true
                override fun isTotalRowException(): Boolean = false
            })
        }
    }

    private fun bindViewModel(){
        viewModel.successEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {data ->
                if (data.isNotEmpty()){
                    Timber.i("userList:success = ", data.toString())
                    userAdapter.addList(data.distinct())
                    viewBinding.rvUser.adapter = userAdapter
                    viewBinding.rvUser.visibility = View.VISIBLE
                    viewBinding.swipeRefresh.isRefreshing = false
                }
            }
        }

        viewModel.errorEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { message ->
                Timber.i("userList:error = ", message)
            }
        }

        viewModel.onLoadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { isLoading ->
                Timber.i("userList:loading = ", isLoading.toString())
                viewBinding.shimmer.visibility = if (isLoading) View.VISIBLE else View.GONE
                if (isLoading)
                    viewBinding.rvUser.visibility = View.GONE
            }
        }

        viewModel.noInternetConnectionEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { state ->
                Timber.i("userList:connection = ", state.toString())
            }
        }
    }

    override fun isHaveInternet() {
        viewModel.getGithubUser(username)
    }

    override fun noInternet() {
        Toast.makeText(requireContext(), "Connection issue", Toast.LENGTH_SHORT).show()
    }
}