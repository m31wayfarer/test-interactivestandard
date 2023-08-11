package com.review.interactivestandard.features.display_points.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.review.interactivestandard.R
import com.review.interactivestandard.common.view.dto.ViewPointDTO
import com.review.interactivestandard.common.view.viewBinding
import com.review.interactivestandard.databinding.FragmentDisplayPointsBinding
import com.review.interactivestandard.utils.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayPointsFragment : Fragment(R.layout.fragment_display_points) {
    private val viewModel: DisplayPointsViewModel by viewModels()
    private val binding by viewBinding(FragmentDisplayPointsBinding::bind)
    private val adapter by lazy(mode = LazyThreadSafetyMode.NONE) { PointsRecyclerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserveState()
    }

    private fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
        setUpLineChart()
    }

    private fun initObserveState() {
        viewModel.state.observeWithLifecycle(viewLifecycleOwner) { state ->
            adapter.submitList(state.tablePoints)
            setDataToLineChart(state.chartPoints)
        }
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