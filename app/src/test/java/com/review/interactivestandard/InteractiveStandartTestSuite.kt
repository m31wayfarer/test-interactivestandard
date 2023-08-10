package com.review.interactivestandard

import com.review.interactivestandard.input_points.InputPointsViewModelUnitTest
import com.review.interactivestandard.mappers.RemotePointMapperUnitTest
import com.review.interactivestandard.mappers.ViewPointMapperUnitTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RemotePointMapperUnitTest::class,
    ViewPointMapperUnitTest::class,
    InputPointsViewModelUnitTest::class
)
class InteractiveStandartTestSuite