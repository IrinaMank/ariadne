package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.cache.cachesource.PointCache
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.mock.RouterApiMock
import io.reactivex.Single

//потом добавим сюда кэш, чтобы лишний раз АПИ не дергать. Будем брать с сервера только если кэш
// пуст или истек его срок актуальности
class PointsRepository(
        private val api: RouterApiMock,
        private val cache: PointCache
) {

    private val mapper = PointMapper()

    fun getPoints(): Single<List<Point>> {
        return if (cache.isExpired()) {
            api.getPoints()
                    .doOnSuccess { cache.saveRepositories(it.map { mapper.fromRemoteToCache(it) }
                    ) }
        } else {
            cache.getPoints().map {
                it.map { mapper.fromCacheToRemote(it)  }
            }
        }
    }
}
