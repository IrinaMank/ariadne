package com.zapir.ariadne.model.interactors

import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.repositories.PointsRepository

//Здесь будет лежать наш алогритм. Он берет список всех точек из репозитория и возвращает
// построенный маршрут
class RouteInteractor(
        private val repository: PointsRepository
) {
    fun createRoute(from: Waypoint, to: Waypoint) =
           listOf<Waypoint>()//ToDo: create algorithm
}