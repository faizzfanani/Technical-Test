package com.faizzfanani.core.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.faizzfanani.core.domain.util.ConnectivityListener

abstract class BaseFragment<T: ViewBinding> : Fragment() {
    private var _viewBinding:T? = null
    protected val viewBinding get() = _viewBinding!!

    protected var connectivityListener: ConnectivityListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = inflate(inflater, container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val connection = BaseConnectivity(requireContext())
        connection.debounce().observe(viewLifecycleOwner) {
            it.contentIfNotHaveBeenHandle?.let {allow ->
                if(connectivityListener != null){
                    if(allow)
                        connectivityListener?.isHaveInternet()
                    else
                        connectivityListener?.noInternet()
                }

                Log.d("IsInternetFragment", allow.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    abstract fun inflate(inflater: LayoutInflater, container: ViewGroup?):T
}