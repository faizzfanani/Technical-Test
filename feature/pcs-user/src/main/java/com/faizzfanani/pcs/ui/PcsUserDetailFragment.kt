package com.faizzfanani.pcs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faizzfanani.core.base.BaseFragment
import com.faizzfanani.core.domain.util.ConnectivityListener
import com.faizzfanani.pcs.PcsFeatureConstant.Companion.EMPTY_STRING
import com.faizzfanani.pcs.PcsFeatureConstant.Companion.KEY_USER_ID
import com.faizzfanani.service_pcs.domain.model.PcsUser
import dagger.hilt.android.AndroidEntryPoint
import id.faizzfanani.technical_test.feature_pcs_user.R
import id.faizzfanani.technical_test.feature_pcs_user.databinding.FragmentPcsDetailUserBinding

@AndroidEntryPoint
class PcsUserDetailFragment : BaseFragment<FragmentPcsDetailUserBinding>(), ConnectivityListener {

    private val viewModel by viewModels<PcsUserDetailViewModel>()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentPcsDetailUserBinding {
        return FragmentPcsDetailUserBinding.inflate(layoutInflater, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        viewModel.getUserDetailByUsername()
        observeLiveData()
    }

    private fun initViews() {
        this.connectivityListener = this
        viewModel.userId = arguments?.getString(KEY_USER_ID) ?: EMPTY_STRING
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

    private fun setDetail(data: PcsUser){
        // assign value
        Glide.with(requireContext()).load(data.avatar).into(viewBinding.avatar)
        viewBinding.detailName.text = data.name
        viewBinding.detailDate.text = getString(R.string.label_created_date, data.createdAt)
        viewBinding.detailAddress.text = data.address
    }

    override fun isHaveInternet() {
        viewModel.getUserDetailByUsername()
    }

    override fun noInternet() {
        Toast.makeText(requireContext(), "Connection issue", Toast.LENGTH_SHORT).show()
    }

}