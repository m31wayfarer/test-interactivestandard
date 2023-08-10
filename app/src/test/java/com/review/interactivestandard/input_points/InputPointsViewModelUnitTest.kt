package com.review.interactivestandard.input_points

import com.review.interactivestandard.R
import com.review.interactivestandard.TimberRule
import com.review.interactivestandard.common.domain.entity.EntityError
import com.review.interactivestandard.common.domain.entity.EntityPoint
import com.review.interactivestandard.common.domain.entity.EntityResult
import com.review.interactivestandard.common.view.mappers.ViewPointMapper
import com.review.interactivestandard.features.input_points.InputPointsViewModel
import com.review.interactivestandard.features.input_points.domain.IPointsInteractor
import com.review.interactivestandard.features.input_points.dto.InputPointsSingleEvent
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import com.review.interactivestandard.utils.IResourceHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test

class InputPointsViewModelUnitTest {
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
    fun `test that isLoading became true when get points started with valid count`() = runTest {
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val interactor = mockk<IPointsInteractor>(relaxed = true)
        every { interactor.getPoints(1) } returns flow {
            delay(1)
            emit(
                IPointsInteractor.GetPointsResult.Success(
                    listOf()
                )
            )
        }
        val mapper = ViewPointMapper()
        val viewModel = InputPointsViewModel(
            dispatchers = dispatchers,
            resourceHelper = resourceHelper,
            pointsInteractor = interactor,
            viewPointMapper = mapper
        )
        assertEquals(false, viewModel.state.value.isLoading)
        viewModel.wantsToGetPoints("1")
        runCurrent()
        assertEquals(true, viewModel.state.value.isLoading)
    }

    @Test
    fun `test that isLoading became false when get points complete success`() = runTest {
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val interactor = mockk<IPointsInteractor>(relaxed = true)
        every { interactor.getPoints(1) } returns flow {
            delay(1)
            emit(
                IPointsInteractor.GetPointsResult.Success(
                    listOf()
                )
            )
        }
        val mapper = ViewPointMapper()
        val viewModel = InputPointsViewModel(
            dispatchers = dispatchers,
            resourceHelper = resourceHelper,
            pointsInteractor = interactor,
            viewPointMapper = mapper
        )
        assertEquals(false, viewModel.state.value.isLoading)
        viewModel.wantsToGetPoints("1")
        runCurrent()
        assertEquals(true, viewModel.state.value.isLoading)
        advanceUntilIdle()
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `test that isLoading became false when get points complete fail`() = runTest {
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val interactor = mockk<IPointsInteractor>(relaxed = true)
        every { interactor.getPoints(1) } returns flow {
            delay(1)
            emit(
                IPointsInteractor.GetPointsResult.Fail(
                    EntityResult.ErrorType.NetworkError
                )
            )
        }
        val mapper = ViewPointMapper()
        val viewModel = InputPointsViewModel(
            dispatchers = dispatchers,
            resourceHelper = resourceHelper,
            pointsInteractor = interactor,
            viewPointMapper = mapper
        )
        assertEquals(false, viewModel.state.value.isLoading)
        viewModel.wantsToGetPoints("1")
        runCurrent()
        assertEquals(true, viewModel.state.value.isLoading)
        advanceUntilIdle()
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `test that show error when count is zero`() = runTest {
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val invalidCountString = "count is invalid"
        every { resourceHelper.getStringByResId(R.string.error_when_count_points_invalid) } returns invalidCountString
        val interactor = mockk<IPointsInteractor>(relaxed = true)
        val mapper = ViewPointMapper()
        val viewModel = InputPointsViewModel(
            dispatchers = dispatchers,
            resourceHelper = resourceHelper,
            pointsInteractor = interactor,
            viewPointMapper = mapper
        )
        viewModel.wantsToGetPoints("0")
        runCurrent()
        assertEquals(
            InputPointsSingleEvent.ShowError(message = invalidCountString),
            viewModel.state.value.event
        )
    }

    @Test
    fun `test that show error when count is negative`() = runTest {
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val invalidCountString = "count is invalid"
        every { resourceHelper.getStringByResId(R.string.error_when_count_points_invalid) } returns invalidCountString
        val interactor = mockk<IPointsInteractor>(relaxed = true)
        val mapper = ViewPointMapper()
        val viewModel = InputPointsViewModel(
            dispatchers = dispatchers,
            resourceHelper = resourceHelper,
            pointsInteractor = interactor,
            viewPointMapper = mapper
        )
        viewModel.wantsToGetPoints("-1")
        runCurrent()
        assertEquals(
            InputPointsSingleEvent.ShowError(message = invalidCountString),
            viewModel.state.value.event
        )
    }

    @Test
    fun `test that show error when count is not_integer`() = runTest {
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val invalidCountString = "count is invalid"
        every { resourceHelper.getStringByResId(R.string.error_when_count_points_invalid) } returns invalidCountString
        val interactor = mockk<IPointsInteractor>(relaxed = true)
        val mapper = ViewPointMapper()
        val viewModel = InputPointsViewModel(
            dispatchers = dispatchers,
            resourceHelper = resourceHelper,
            pointsInteractor = interactor,
            viewPointMapper = mapper
        )
        viewModel.wantsToGetPoints("1.1")
        runCurrent()
        assertEquals(
            InputPointsSingleEvent.ShowError(message = invalidCountString),
            viewModel.state.value.event
        )
    }

    @Test
    fun `test that when_do_wants_to_gets_points_during_of_loading_then_it_will_be_ignored`() =
        runTest {
            val resourceHelper = mockk<IResourceHelper>(relaxed = true)
            val interactor = mockk<IPointsInteractor>(relaxed = true)
            every { interactor.getPoints(1) } returns flow {
                delay(1)
                emit(
                    IPointsInteractor.GetPointsResult.Success(
                        listOf()
                    )
                )
            }
            val mapper = ViewPointMapper()
            val viewModel = InputPointsViewModel(
                dispatchers = dispatchers,
                resourceHelper = resourceHelper,
                pointsInteractor = interactor,
                viewPointMapper = mapper
            )
            viewModel.wantsToGetPoints("1")
            runCurrent()
            viewModel.wantsToGetPoints("1")
            runCurrent()
            verify(exactly = 1) {
                interactor.getPoints(1)
            }
        }

    @Test
    fun `test that show error when get_points_request_is_failed`() = runTest {
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val interactor = mockk<IPointsInteractor>(relaxed = true)
        val errorMessage = "the count is invalid"
        every { interactor.getPoints(1001) } returns flow {
            emit(
                IPointsInteractor.GetPointsResult.Fail(
                    EntityResult.ErrorType.GenericError(error = EntityError(message = errorMessage))
                )
            )
        }
        val mapper = ViewPointMapper()
        val viewModel = InputPointsViewModel(
            dispatchers = dispatchers,
            resourceHelper = resourceHelper,
            pointsInteractor = interactor,
            viewPointMapper = mapper
        )
        viewModel.wantsToGetPoints("1001")
        runCurrent()
        assertEquals(
            InputPointsSingleEvent.ShowError(message = errorMessage),
            viewModel.state.value.event
        )
    }

    @Test
    fun `test that got_the_next_screen when get_points_request_is_success`() = runTest {
        val resourceHelper = mockk<IResourceHelper>(relaxed = true)
        val interactor = mockk<IPointsInteractor>(relaxed = true)
        val points =
            listOf(
                EntityPoint(1.1f, 2.2f),
                EntityPoint(2.3f, 3.5f),
                EntityPoint(3.1f, 6.7f)
            )
        every { interactor.getPoints(1000) } returns flow {
            emit(
                IPointsInteractor.GetPointsResult.Success(
                    points
                )
            )
        }
        val mapper = ViewPointMapper()
        val viewModel = InputPointsViewModel(
            dispatchers = dispatchers,
            resourceHelper = resourceHelper,
            pointsInteractor = interactor,
            viewPointMapper = mapper
        )
        viewModel.wantsToGetPoints("1000")
        runCurrent()
        assertEquals(
            InputPointsSingleEvent.DisplayPoints(points = points.map { point ->
                mapper.mapToView(
                    point
                )
            }),
            viewModel.state.value.event
        )
    }
}