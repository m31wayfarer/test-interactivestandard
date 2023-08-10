package com.review.interactivestandard.mappers

import com.review.interactivestandard.TimberRule
import com.review.interactivestandard.common.domain.entity.EntityPoint
import com.review.interactivestandard.common.view.mappers.ViewPointMapper
import junit.framework.TestCase.assertEquals
import org.junit.ClassRule
import org.junit.Test

class ViewPointMapperUnitTest {
    companion object {
        @get:ClassRule
        @JvmStatic
        var timberRule = TimberRule()
    }

    @Test
    fun `test correct mapping from entity to view, coordinate x`() {
        val mapper = ViewPointMapper()
        val entityPoint = EntityPoint(x = 1.232f, y = 5.24f)
        val viewPoint = mapper.mapToView(entityPoint)
        assertEquals(entityPoint.x, viewPoint.x)
    }

    @Test
    fun `test correct mapping from entity to view, coordinate y`() {
        val mapper = ViewPointMapper()
        val entityPoint = EntityPoint(x = 1.232f, y = 5.24f)
        val viewPoint = mapper.mapToView(entityPoint)
        assertEquals(entityPoint.y, viewPoint.y)
    }
}