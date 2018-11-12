package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.cache.cachesource.PointCache
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.mock.RouterApiMock
import io.reactivex.Single

class PointsRepository(
        private val api: RouterApiMock,
        private val cache: PointCache
) {

    private val mapper = PointMapper()

    fun getPoints(): Single<List<Point>> =
        cache.isExpired().flatMap {
            if (it) {
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
