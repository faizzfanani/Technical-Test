package com.faizzfanani.pcs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.faizzfanani.core.base.BaseFragment
import com.faizzfanani.core.domain.util.ConnectivityListener
import com.faizzfanani.core_ui.text_style.bodyRegularStyle
import com.faizzfanani.core_ui.text_style.pageHeaderStyle
import com.faizzfanani.core_ui.text_style.titleStyle
import com.faizzfanani.navigation.goToPcsUserDetail
import com.faizzfanani.pcs.PcsFeatureConstant.Companion.EMPTY_STRING
import com.faizzfanani.pcs.component.GithubUserSearchView
import com.faizzfanani.pcs.component.ShimmerUserItems
import com.faizzfanani.service_pcs.domain.model.PcsUser
import dagger.hilt.android.AndroidEntryPoint
import id.faizzfanani.technical_test.feature_pcs_user.databinding.FragmentPcsUserListBinding
import kotlinx.coroutines.delay
import id.faizzfanani.technical_test.core_ui.R as coreUI_R

@AndroidEntryPoint
class PcsUserListFragment : BaseFragment<FragmentPcsUserListBinding>(), ConnectivityListener {

    private val viewModel by viewModels<PcsUserListViewModel>()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentPcsUserListBinding {
        return FragmentPcsUserListBinding.inflate(layoutInflater, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.composeView.setContent {
            MaterialTheme {
                ParentLayout()
            }
        }
        initViews()
        viewModel.getPcsUserList()
    }

    private fun initViews(){
        this.connectivityListener = this
    }

    override fun isHaveInternet() {
        viewModel.getPcsUserList()
    }

    override fun noInternet() {
        Toast.makeText(requireContext(), "Connection issue", Toast.LENGTH_SHORT).show()
    }

    // UI compose section

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ParentLayout() {
        var isRefreshing by remember { mutableStateOf(viewModel.isLoading) }
        var searchQuery by remember { mutableStateOf(EMPTY_STRING) }
        val userList by viewModel.userList.collectAsState()
        val searchList = when {
            searchQuery.isEmpty() -> userList
            else -> userList.filter { user -> user.name.contains(searchQuery, ignoreCase = true) }
        }

        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
            }
        )

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                delay(1000)
                viewModel.getPcsUserList()
                isRefreshing = false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = coreUI_R.color.white))
                .padding(16.dp)
                .pullRefresh(pullRefreshState)
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PageTitle()
                GithubUserSearchView(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                    },
                    onSearch = {
                        searchQuery = it
                    }
                )
                UserList(isLoading = isRefreshing, userList = searchList)
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }

    @Composable
    fun PageTitle() {
        Text(
            text = getString(coreUI_R.string.app_name),
            style = pageHeaderStyle,
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    @Composable
    fun UserList(isLoading: Boolean, userList: List<PcsUser>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (isLoading)
                items(10) {
                    ShimmerUserItems()
                }
            else
                items(userList) { user ->
                    ItemCard(user)
                }
        }
    }

    @Composable
    fun ItemCard(user: PcsUser) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    goToPcsUserDetail(view = requireView(), data = user.id)
                },
            shape = RoundedCornerShape(5.dp), // Rounded corners
            colors = CardDefaults.cardColors(containerColor = colorResource(id = coreUI_R.color.white)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.avatar)
                        .crossfade(true)
                        .placeholder(coreUI_R.drawable.ic_person_circle)
                        .build(),
                    contentDescription = "user avatar",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(style = titleStyle, text = user.name, color = colorResource(coreUI_R.color.black))
                    Spacer(Modifier.height(2.dp))
                    Text(
                        style = bodyRegularStyle,
                        text = user.address,
                        color = colorResource(coreUI_R.color.grey_21))
                }
            }
        }
    }
}