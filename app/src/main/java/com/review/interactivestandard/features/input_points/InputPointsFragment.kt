package com.review.interactivestandard.features.input_points

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.review.interactivestandard.R
import com.review.interactivestandard.common.view.dto.ViewPointDTO
import com.review.interactivestandard.common.view.viewBinding
import com.review.interactivestandard.databinding.FragmentInputPointsBinding
import com.review.interactivestandard.features.input_points.dto.InputPointsSingleEvent
import com.review.interactivestandard.utils.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputPointsFragment : Fragment(R.layout.fragment_input_points) {
    private val viewModel: InputPointsViewModel by viewModels()
    private val binding by viewBinding(FragmentInputPointsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserveState()
    }

    private fun initView() {
        with(binding) {
            btnGo.setOnClickListener {
                viewModel.wantsToGetPoints(tilNumberOfPoints.editText?.text.toString())
            }
        }
    }

    private fun initObserveState() {
        viewModel.state.observeWithLifecycle(viewLifecycleOwner) { state ->
            state.event?.let { event ->
                when (event) {
                    is InputPointsSingleEvent.DisplayPoints -> {
                        goToDisplayPoints(points = event.points)
                    }

                    is InputPointsSingleEvent.ShowError -> {
                        showError(event.message)
                    }
                }
                viewModel.didHandleEvent()
            }
            if (state.isLoading)
                binding.progressIndicator.show()
            else
                binding.progressIndicator.hide()
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun goToDisplayPoints(points: List<ViewPointDTO>) {
        findNavController().navigate(
            InputPointsFragmentDirections
                .actionInputPointsFragmentToDisplayPointsFragment(points = points.toTypedArray())
        )
    }
}