package com.zapir.ariadne.model.points

import com.zapir.ariadne.model.RouterApi

class PointsRepository {
    private val api = RouterApi()

   // fun getPoint() = api.getPoints()

    fun getBuilding() = api.getBuilding().subscribe(
        {
            System.out.print("1")
        },
        {
            System.out.print("2")
        }
    )
}
