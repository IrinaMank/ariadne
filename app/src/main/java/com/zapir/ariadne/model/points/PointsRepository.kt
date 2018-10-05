package com.zapir.ariadne.model.points

import com.zapir.ariadne.model.RouterApi
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.mock.RouterApiMock
import io.reactivex.Single

class PointsRepository {
    private val api = RouterApiMock()

    fun getPoints(): Single<MutableList<Point>> = api.getPoints()
}
