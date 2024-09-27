package com.faizzfanani.navigation

import android.net.Uri
import android.view.View
import androidx.navigation.findNavController
import id.faizzfanani.`technical-test`.navigation.R

fun goToUserList(view: View, data:Any? = null){
    view.findNavController().navigate(Uri.parse(view.context.getString(R.string.github_list_url)))
}
fun goToUserDetail(view: View, data:Any? = null){
    view.findNavController().navigate(Uri.parse(view.context.getString(R.string.github_detail_url)))
}
fun back(view: View){
    view.findNavController().popBackStack()
}