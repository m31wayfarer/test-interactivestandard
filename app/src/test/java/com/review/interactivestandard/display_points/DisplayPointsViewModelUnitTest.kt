package com.review.interactivestandard.display_points

import androidx.lifecycle.SavedStateHandle
import com.review.interactivestandard.R
import com.review.interactivestandard.TimberRule
import com.review.interactivestandard.common.view.dto.ViewPointDTO
import com.review.interactivestandard.features.display_points.domain.IImageInteractor
import com.review.interactivestandard.features.display_points.domain.entity.Image
import com.review.interactivestandard.features.display_points.domain.entity.ShareImageInfo
import com.review.interactivestandard.features.display_points.view.DisplayPointsViewModel
import com.review.interactivestandard.features.display_points.view.dto.DisplayPointsSingleEvent
import com.review.interactivestandard.features.display_points.view.mappers.TableViewPointMapper
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import com.review.interactivestandard.utils.IResourceHelper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
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
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val imageInteractor = mockk<IImageInteractor>(relaxed = true)
        val viewModel = DisplayPointsViewModel(
            dispatchers = dispatchers,
            savedStateHandle = savedStateHandle,
            mapper = mapper,
            resourceHelper = resourceHelper,
            imageInteractor = imageInteractor
        )
        runCurrent()
        assertEquals(
            points.toList().sortedBy { point -> point.x },
            viewModel.state.value.chartPoints
        )
    }

    @Test
    fun `test that_sharing_image_fail`() = runTest {
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
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val errorMsg = "sharing chart has been failed"
        every { resourceHelper.getStringByResId(R.string.error_during_sharing_chart_image) } returns
                errorMsg
        val imageInteractor = mockk<IImageInteractor>(relaxed = true)
        coEvery { imageInteractor.saveImageToShare(any()) } returns null

        val viewModel = DisplayPointsViewModel(
            dispatchers = dispatchers,
            savedStateHandle = savedStateHandle,
            mapper = mapper,
            resourceHelper = resourceHelper,
            imageInteractor = imageInteractor
        )

        viewModel.shareImage(Image(2, 2, byteArrayOf(1, 2, 3, 4)))

        runCurrent()

        assertEquals(
            DisplayPointsSingleEvent.ShowError(message = errorMsg),
            viewModel.event.value
        )

        viewModel.didHandleEvent()
        assertEquals(
            null,
            viewModel.event.value
        )
    }

    @Test
    fun `test that_sharing_image_success`() = runTest {
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
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val chooserTitle = "sharing chart title"
        every { resourceHelper.getStringByResId(R.string.chooser_title_for_sharing_chart_image) } returns
                chooserTitle
        val imageInteractor = mockk<IImageInteractor>(relaxed = true)
        val shareInfo = ShareImageInfo("uriString", "image/jpeg")
        coEvery { imageInteractor.saveImageToShare(any()) } returns shareInfo

        val viewModel = DisplayPointsViewModel(
            dispatchers = dispatchers,
            savedStateHandle = savedStateHandle,
            mapper = mapper,
            resourceHelper = resourceHelper,
            imageInteractor = imageInteractor
        )

        viewModel.shareImage(Image(2, 2, byteArrayOf(1, 2, 3, 4)))

        runCurrent()

        assertEquals(
            DisplayPointsSingleEvent.ShareImage(
                shareImageInfo = shareInfo,
                chooserTitle = chooserTitle
            ),
            viewModel.event.value
        )

        viewModel.didHandleEvent()
        assertEquals(
            null,
            viewModel.event.value
        )
    }
}