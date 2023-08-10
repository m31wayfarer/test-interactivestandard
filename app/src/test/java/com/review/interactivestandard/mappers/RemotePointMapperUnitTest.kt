package com.review.interactivestandard.mappers

import com.review.interactivestandard.TimberRule
import com.review.interactivestandard.features.input_points.data.remote.dto.PointDTO
import com.review.interactivestandard.features.input_points.data.remote.mappers.RemotePointMapper
import junit.framework.TestCase.assertEquals
import org.junit.ClassRule
import org.junit.Test

class RemotePointMapperUnitTest {
    companion object {
        @get:ClassRule
        @JvmStatic
        var timberRule = TimberRule()
    }

    @Test
    fun `test correct mapping from remote to entity, coordinate x`() {
        val mapper = RemotePointMapper()
        val remotePoint = PointDTO(x = 7.222f, y = 8.22f)
        val entityPoint = mapper.mapToEntity(remotePoint)
        assertEquals(remotePoint.x, entityPoint.x)
    }

    @Test
    fun `test correct mapping from remote to entity, coordinate y`() {
        val mapper = RemotePointMapper()
        val remotePoint = PointDTO(x = 7.222f, y = 8.22f)
        val entityPoint = mapper.mapToEntity(remotePoint)
        assertEquals(remotePoint.y, entityPoint.y)
    }
}