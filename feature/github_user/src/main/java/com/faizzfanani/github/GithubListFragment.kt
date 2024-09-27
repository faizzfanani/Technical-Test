package com.faizzfanani.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.faizzfanani.core.base.BaseFragment
import com.faizzfanani.core.domain.util.ConnectivityListener
import com.faizzfanani.core_ui.component.PaginationScrollListener
import com.faizzfanani.service_github.domain.model.GithubUser
import dagger.hilt.android.AndroidEntryPoint
import id.faizzfanani.technical_test.feature_github_user.R
import id.faizzfanani.technical_test.feature_github_user.databinding.FragmentGithubListBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class GithubListFragment : BaseFragment<FragmentGithubListBinding>(), ConnectivityListener {

    private val viewModel by viewModels<GithubListViewModel>()
    private val userAdapter
        get() = viewBinding.rvUser.adapter as UserAdapter

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentGithubListBinding {
        return FragmentGithubListBinding.inflate(layoutInflater, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
    }

    private fun initViews(){
        this.connectivityListener = this

        viewBinding.swipeRefresh.setOnRefreshListener {
            viewModel.getGithubUser()
        }

        viewBinding.labelReset.setOnClickListener {
            viewModel.getGithubUser()
        }

        viewBinding.btnLoadMore.setOnClickListener {
            viewModel.currentPage++
            viewModel.getGithubUser()
        }

        viewBinding.rvUser.apply {
            setHasFixedSize(true)
            adapter = UserAdapter {
                viewModel.searchGithubUser(username = it)
            }
            addOnScrollListener(object : PaginationScrollListener() {
                override fun loadMoreItems() {
                    viewBinding.btnLoadMore.visibility = View.VISIBLE
                    viewBinding.progressCircular.visibility = View.GONE
                }
                override fun getTotalRowCount(): Int = viewModel.pageSize
                override fun isLastPage(): Boolean = viewModel.isLastPage
                override fun isLoading(): Boolean = viewModel.isLoading
                override fun isTotalRowException(): Boolean = false
            })
        }

        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty())
                        viewModel.searchGithubUser(it)
                    else{
                        viewModel.getGithubUser()
                        viewBinding.showDetail.root.visibility = View.GONE
                    }

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()){
                        viewModel.viewModelScope.launch {
                            delay(1000)
                            viewModel.searchGithubUser(it)
                        }
                    } else{
                        viewModel.getGithubUser()
                        viewBinding.showDetail.root.visibility = View.GONE
                    }
                }
                return false
            }
        })
    }

    private fun bindViewModel(){
        viewModel.successListEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {data ->
                if (data.isNotEmpty()){
                    Timber.d("userList:success = ", data.toString())
                    userAdapter.addList(data.distinct())
                    viewBinding.rvUser.adapter = userAdapter
                    viewBinding.rvUser.visibility = View.VISIBLE
                    viewBinding.labelReset.visibility = View.GONE
                    viewBinding.labelDiscover.text = getString(R.string.label_discover)
                }
            }
        }

        viewModel.successDetailEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {data ->
                Timber.d("userDetail:success = ", data.toString())
                setDetail(data)
                viewBinding.rvUser.visibility = View.GONE
            }
        }

        viewModel.errorEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { message ->
                Timber.d("userList:error = ", message)
            }
        }

        viewModel.onLoadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { isLoading ->
                Timber.d("userList:loading = ", isLoading.toString())
                viewBinding.shimmer.visibility = if (isLoading) View.VISIBLE else View.GONE
                viewBinding.swipeRefresh.isRefreshing = isLoading
                if (isLoading){
                    viewBinding.showDetail.root.visibility = View.GONE
                    viewBinding.rvUser.visibility = View.GONE
                    viewBinding.btnLoadMore.progressingAnimation()
                    viewBinding.progressCircular.visibility = viewBinding.btnLoadMore.visibility
                } else{
                    viewBinding.btnLoadMore.reverseAnimation()
                    viewBinding.btnLoadMore.visibility = View.GONE
                }
            }
        }

        viewModel.noInternetConnectionEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { state ->
                Timber.i("userList:connection = ", state.toString())
            }
        }
    }

    private fun setDetail(data: GithubUser){

        Glide.with(requireContext()).load(data.avatarUrl).into(viewBinding.showDetail.avatar)
        Glide.with(requireContext()).load(data.avatarUrl).into(viewBinding.showDetail.detailUser.avatar)
        viewBinding.showDetail.detailUser.detailName.text = data.name.ifEmpty { data.username }
        viewBinding.showDetail.itemUsername.text = data.username
        viewBinding.showDetail.itemEmail.text = data.bio
        viewBinding.showDetail.detailUser.detailUsername.text = data.username
        viewBinding.showDetail.detailUser.detailBio.text = data.bio
        viewBinding.showDetail.detailUser.detailCompany.text = getString(R.string.user_company, data.company)
        viewBinding.showDetail.detailUser.detailFollowers.text = getString(R.string.user_follower, data.followersCount.toString())
        viewBinding.showDetail.detailUser.detailCompany.text = getString(R.string.user_following, data.followingCount.toString())

        viewBinding.labelDiscover.text = getString(R.string.user_search_result, data.username)

        viewBinding.showDetail.detailUser.detailCompany.visibility = if (data.company.isEmpty()) View.GONE else View.VISIBLE
        viewBinding.showDetail.detailUser.detailFollowers.visibility = if (data.followersCount <= 0) View.GONE else View.VISIBLE
        viewBinding.showDetail.detailUser.detailFollowings.visibility = if (data.followingCount <= 0) View.GONE else View.VISIBLE

        viewBinding.showDetail.root.visibility = View.VISIBLE
        viewBinding.showDetail.detailUser.root.visibility = View.VISIBLE
        viewBinding.labelReset.visibility = View.VISIBLE
    }

    override fun isHaveInternet() {
        viewModel.getGithubUser()
    }

    override fun noInternet() {
        Toast.makeText(requireContext(), "Connection issue", Toast.LENGTH_SHORT).show()
    }
}