package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.cache.entity.PointEntity
import com.zapir.ariadne.model.entity.Point
import java.util.*

class PointMapper {

    fun fromRemoteToCache(remote: Point) =
            PointEntity(
                    UUID.randomUUID().toString(),
                    remote.name,
                    remote.x,
                    remote.y
            )

    fun fromCacheToRemote(cache: PointEntity) =
            Point(
                    cache.name,
                    cache.x,
                    cache.y
            )
}