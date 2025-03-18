package com.faizzfanani.github.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faizzfanani.core.base.BaseFragment
import com.faizzfanani.core.domain.util.ConnectivityListener
import com.faizzfanani.github.GithubFeatureConstant.Companion.EMPTY_STRING
import com.faizzfanani.github.GithubFeatureConstant.Companion.KEY_USERNAME
import com.faizzfanani.service_github.domain.model.GithubUser
import dagger.hilt.android.AndroidEntryPoint
import id.faizzfanani.technical_test.feature_github_user.R
import id.faizzfanani.technical_test.feature_github_user.databinding.FragmentDetailUserBinding

@AndroidEntryPoint
class GithubDetailFragment : BaseFragment<FragmentDetailUserBinding>(), ConnectivityListener {

    private val viewModel by viewModels<GithubDetailViewModel>()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailUserBinding {
        return FragmentDetailUserBinding.inflate(layoutInflater, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        viewModel.getUserDetailByUsername()
        observeLiveData()
    }

    private fun initViews() {
        this.connectivityListener = this
        viewModel.username = arguments?.getString(KEY_USERNAME) ?: EMPTY_STRING
    }

    private fun observeLiveData() {
        viewModel.successDetailEvent.observe(viewLifecycleOwner) {
            it.contentIfNotHaveBeenHandle?.let { user ->
                setDetail(user)
                viewBinding.layoutDetail.visibility = View.VISIBLE
            }
        }
        viewModel.errorEvent.observe(viewLifecycleOwner) {
            it.contentIfNotHaveBeenHandle?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewBinding.layoutDetail.visibility = View.GONE
            }
        }
        viewModel.onLoadingEvent.observe(viewLifecycleOwner) {
            it.contentIfNotHaveBeenHandle?.let { isLoading ->
                viewBinding.shimmerDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
                viewBinding.layoutDetail.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
        }
    }

    private fun setDetail(data: GithubUser){
        // assign value
        Glide.with(requireContext()).load(data.avatarUrl).into(viewBinding.avatar)
        viewBinding.detailName.text = data.name.ifEmpty { data.username }
        viewBinding.detailUsername.text = data.username
        viewBinding.detailEmail.text = data.email
        viewBinding.detailBio.text = data.bio
        viewBinding.detailCompany.text = getString(R.string.user_company, data.company)
        viewBinding.detailFollowers.text = getString(R.string.user_follower, data.followersCount.toString())
        viewBinding.detailFollowings.text = getString(R.string.user_following, data.followingCount.toString())

        // set visibility
        viewBinding.detailEmail.visibility = if (data.email.isEmpty()) View.GONE else View.VISIBLE
        viewBinding.detailCompany.visibility = if (data.company.isEmpty()) View.GONE else View.VISIBLE
        viewBinding.detailFollowers.visibility = if (data.followersCount <= 0) View.GONE else View.VISIBLE
        viewBinding.detailFollowings.visibility = if (data.followingCount <= 0) View.GONE else View.VISIBLE
    }

    override fun isHaveInternet() {
        viewModel.getUserDetailByUsername()
    }

    override fun noInternet() {
        Toast.makeText(requireContext(), "Connection issue", Toast.LENGTH_SHORT).show()
    }

}