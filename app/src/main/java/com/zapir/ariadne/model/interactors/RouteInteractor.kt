package com.zapir.ariadne.model.interactors

import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.entity.common.Point
import com.zapir.ariadne.model.repositories.PointsRepository

//Здесь будет лежать наш алогритм. Он берет список всех точек из репозитория и возвращает
// построенный маршрут
class RouteInteractor(
        private val repository: PointsRepository
) {
    fun createRoute(from: Waypoint, to: Waypoint) =
           listOf<Waypoint>(
                   Waypoint(5, coordinates = Point(300f,300f)),
                   Waypoint(3, coordinates = Point(300f,1300f)),
                   Waypoint(6, coordinates = Point(1500f,1300f))
           )//ToDo: create algorithm
}