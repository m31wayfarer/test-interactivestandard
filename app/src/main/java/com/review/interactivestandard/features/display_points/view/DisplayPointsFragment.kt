package com.review.interactivestandard.features.display_points.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.snackbar.Snackbar
import com.review.interactivestandard.R
import com.review.interactivestandard.common.view.dto.ViewPointDTO
import com.review.interactivestandard.common.view.viewBinding
import com.review.interactivestandard.databinding.FragmentDisplayPointsBinding
import com.review.interactivestandard.features.display_points.domain.entity.ShareImageInfo
import com.review.interactivestandard.features.display_points.view.dto.DisplayPointsSingleEvent
import com.review.interactivestandard.features.display_points.view.mappers.ImageMapper
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import com.review.interactivestandard.utils.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DisplayPointsFragment : Fragment(R.layout.fragment_display_points) {
    private val viewModel: DisplayPointsViewModel by viewModels()
    private val binding by viewBinding(FragmentDisplayPointsBinding::bind)
    private val adapter by lazy(mode = LazyThreadSafetyMode.NONE) { PointsRecyclerAdapter() }

    @Inject
    lateinit var imageMapper: ImageMapper

    @Inject
    lateinit var dispatchers: AppCoroutineDispatchers

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserveState()
        initObserveEvent()
    }

    private fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            shareButton.setOnClickListener {
                wantsToShareChart()
            }
        }
        setUpLineChart()
    }

    private fun wantsToShareChart() {
        viewLifecycleOwner.lifecycleScope.launch(dispatchers.computation) {
            val bitmap = binding.chart.drawToBitmap()
            viewModel.shareImage(imageMapper.mapToImage(bitmap))
        }
    }

    private fun initObserveState() {
        viewModel.state.observeWithLifecycle(viewLifecycleOwner) { state ->
            with(state) {
                adapter.submitList(tablePoints)
                setDataToLineChart(chartPoints)
            }
        }
    }

    private fun initObserveEvent() {
        viewModel.event.filterNotNull().observeWithLifecycle(viewLifecycleOwner) { event ->
            when (event) {
                is DisplayPointsSingleEvent.ShareImage -> {
                    shareImage(event.shareImageInfo, event.chooserTitle)
                }

                is DisplayPointsSingleEvent.ShowError -> {
                    showError(event.message)
                }
            }
            viewModel.didHandleEvent()
        }
    }

    private fun shareImage(shareImageInfo: ShareImageInfo, chooserTitle: String) {
        val shareIntent = ShareCompat.IntentBuilder(requireContext())
            .setType(shareImageInfo.imageType)
            .setStream(Uri.parse(shareImageInfo.uriString))
            .intent
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(shareIntent, chooserTitle))
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun setUpLineChart() {
        with(binding.chart) {
            description.isEnabled = false

            xAxis.setDrawGridLines(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1F
            xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.axe_text_color)
            axisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.axe_text_color)
            axisRight.isEnabled = false
            legend.isEnabled = false

            setTouchEnabled(true)
            setPinchZoom(true)
            isDoubleTapToZoomEnabled = true
        }
    }

    private fun setDataToLineChart(points: List<ViewPointDTO>) {
        val pointsDataSet = LineDataSet(points.map { point -> Entry(point.x, point.y) }, "")
        pointsDataSet.lineWidth = 1f
        pointsDataSet.disableDashedLine()
        pointsDataSet.setDrawValues(false)
        pointsDataSet.setDrawCircles(false)
        pointsDataSet.mode = LineDataSet.Mode.LINEAR
        pointsDataSet.color = ContextCompat.getColor(requireContext(), R.color.line_data_color)

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(pointsDataSet)

        val lineData = LineData(dataSet)
        binding.chart.data = lineData

        binding.chart.invalidate()
    }
}