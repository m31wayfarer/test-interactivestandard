package com.review.interactivestandard.mappers

import com.review.interactivestandard.TimberRule
import com.review.interactivestandard.common.view.dto.ViewPointDTO
import com.review.interactivestandard.features.display_points.view.mappers.TableViewPointMapper
import junit.framework.TestCase
import org.junit.ClassRule
import org.junit.Test

class TableViewPointMapperUnitTest {
    companion object {
        @get:ClassRule
        @JvmStatic
        var timberRule = TimberRule()
    }

    @Test
    fun `test correct mapping from view to table view point, coordinate x`() {
        val mapper = TableViewPointMapper()
        val viewPoint = ViewPointDTO(x = 1.232f, y = 5.24f)
        val tableViewPoint = mapper.mapToTableViewPoint(viewPoint)
        TestCase.assertEquals(viewPoint.x.toString(), tableViewPoint.x)
    }

    @Test
    fun `test correct mapping from entity to table view point, coordinate y`() {
        val mapper = TableViewPointMapper()
        val viewPoint = ViewPointDTO(x = 1.232f, y = 5.24f)
        val tableViewPoint = mapper.mapToTableViewPoint(viewPoint)
        TestCase.assertEquals(viewPoint.y.toString(), tableViewPoint.y)
    }
}