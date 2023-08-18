package com.faizfanani.movieapp.ui.view.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.faizfanani.movieapp.databinding.BottomSheetNoActionBinding
import com.faizfanani.movieapp.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NoActionBottomSheet : BottomSheetDialogFragment() {
    private var mBinding: BottomSheetNoActionBinding? = null
    private val binding
        get() = mBinding!!

    companion object {
        private const val TITLE = "TITLE"
        private const val DESC = "DESC"
        private const val AUTO_DISMISS = "autoDismiss"
        private const val IS_CANCELABLE = "isCancelable"
        private const val REQUEST_KEY = "requestKey"
        fun newInstance(
            title: String,
            desc: String?,
            autoDismiss: Long = 0L,
            isCancelable: Boolean = true,
            requestKey: String? = null,
        ): NoActionBottomSheet {
            return NoActionBottomSheet().apply {
                arguments = bundleOf(
                    TITLE to title,
                    DESC to desc,
                    AUTO_DISMISS to autoDismiss,
                    IS_CANCELABLE to isCancelable,
                    REQUEST_KEY to requestKey,
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = BottomSheetNoActionBinding.inflate(inflater, container, false)
        binding.tvTitle.text = arguments?.getString(TITLE) ?: ""
        binding.tvDescription.text = arguments?.getString(DESC) ?: ""
        var autoDismiss = arguments?.getLong(AUTO_DISMISS) ?: 0L

        if (arguments?.getString(DESC).isNullOrEmpty()) {
            binding.tvDescription.visibility = View.GONE
        }

        if (arguments?.getBoolean(IS_CANCELABLE) == false) {
            autoDismiss = 0L
            isCancelable = false
            binding.notch.visibility = View.GONE
        }

        if (autoDismiss > 0L) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    if (isActive && isAdded) {
                        delay(autoDismiss)
                        dismiss()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onDismiss(dialog: DialogInterface) {
        arguments?.getString(REQUEST_KEY)?.let {
            parentFragmentManager.setFragmentResult(
                it,
                bundleOf(Constants.ACTION_RESULT to Constants.RESULT_DISMISS)
            )
        }
        super.onDismiss(dialog)
    }
}