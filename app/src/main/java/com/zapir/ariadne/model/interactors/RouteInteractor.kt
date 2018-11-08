package com.zapir.ariadne.model.interactors

import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.repositories.PointsRepository

//Здесь будет лежать наш алогритм. Он берет список всех точек из репозитория и возвращает
// построенный маршрут
class RouteInteractor(
        private val repository: PointsRepository
) {
    fun createRoute(from: Point, to: Point) =
           listOf<Point>(Point("dlkf"), Point("dlkf"), Point("dlkf"))
}