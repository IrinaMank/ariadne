package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.mock.RouterApiMock
import io.reactivex.Single

//потом добавим сюда кэш, чтобы лишний раз АПИ не дергать. Будем брать с сервера только если кэш
// пуст или истек его срок актуальности
class PointsRepository {
    private val api = RouterApiMock()

    fun getPoints(): Single<MutableList<Point>> = api.getPoints()
}
