package com.review.interactivestandard.features.input_points

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.review.interactivestandard.R
import com.review.interactivestandard.common.view.viewBinding
import com.review.interactivestandard.databinding.FragmentInputPointsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputPointsFragment: Fragment(R.layout.fragment_input_points) {
    private val viewModel: InputPointsViewModel by viewModels()
    private val binding by viewBinding(FragmentInputPointsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserveState()
    }
    private fun initView() {
        with(binding){

        }
    }
    private fun initObserveState() {

    }
}