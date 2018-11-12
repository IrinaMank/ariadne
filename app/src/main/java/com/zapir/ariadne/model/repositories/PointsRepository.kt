package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.cache.cachesource.PointCache
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.mock.RouterApiMock
import com.zapir.ariadne.model.remote.RouterApi
import io.reactivex.Single

class PointsRepository(
        private val api: RouterApi,
        private val cache: PointCache
) {

    private val mapper = PointMapper()

    fun getPoints(): Single<List<Waypoint>> =
        cache.isExpired().flatMap {
            if (it) {
                api.getBuilding()
                        .map { it.points }
                        .doOnSuccess { cache.saveRepositories(it.map { mapper.fromRemoteToCache(it) }
                        ) }
            } else {
                cache.getPoints().map {
                    it.map { mapper.fromCacheToRemote(it)  }
                }
            }
    }
}
