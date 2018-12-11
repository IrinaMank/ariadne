package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.cache.cachesource.PointCache
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.remote.RouterApi
import io.reactivex.Observable
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

    fun getPointsById(id: Int): Single<Waypoint> =
                    cache.getPoints(id).map {
                         mapper.fromCacheToRemote(it)
            }

    fun getPointsOnFloor(id: Int) =
            getPoints()
                    .flatMapObservable { list -> Observable.fromIterable(list)}
                    .filter { point -> point.floor == id }
                    .toList()

    fun getStatic() = api.getFloorUrls().subscribe(
            {
                System.out.print("1")
            },
            {
                System.out.print("2")
            }
    )
}
