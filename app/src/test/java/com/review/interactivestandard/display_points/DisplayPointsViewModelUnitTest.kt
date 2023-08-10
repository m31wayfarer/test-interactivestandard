package com.review.interactivestandard.display_points

import androidx.lifecycle.SavedStateHandle
import com.review.interactivestandard.TimberRule
import com.review.interactivestandard.common.view.dto.ViewPointDTO
import com.review.interactivestandard.features.display_points.view.DisplayPointsViewModel
import com.review.interactivestandard.features.display_points.view.mappers.TableViewPointMapper
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test

class DisplayPointsViewModelUnitTest {
    companion object {
        @get:ClassRule
        @JvmStatic
        var timberRule = TimberRule()
    }

    private val dispatcher = StandardTestDispatcher()
    private val dispatchers = AppCoroutineDispatchers(
        io = dispatcher, main = dispatcher, computation = dispatcher, mainImmediate = dispatcher
    )

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test that_chart_points_sorted_by_x`() = runTest {
        val mapper = TableViewPointMapper()
        val points =
            arrayOf(
                ViewPointDTO(1.1f, 2.2f),
                ViewPointDTO(1.12f, 2.73f),
                ViewPointDTO(0.9f, 2.23f),
                ViewPointDTO(0.5f, 2.236f),
                ViewPointDTO(3.0f, 2.236f)
            )
        val savedStateHandle = SavedStateHandle().apply {
            set("points", points)
        }
        val viewModel = DisplayPointsViewModel(
            dispatchers = dispatchers,
            savedStateHandle = savedStateHandle,
            mapper = mapper
        )
        runCurrent()
        assertEquals(
            points.toList().sortedBy { point -> point.x },
            viewModel.state.value.chartPoints
        )
    }
}