package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.cache.entity.PointEntity
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.entity.common.Point
import java.util.*

class PointMapper {

    //ToDo: id from server
    fun fromRemoteToCache(remote: Waypoint) =
            PointEntity(
                    remote.id,
                    remote.name,
                    remote.coordinates.x,
                    remote.coordinates.y,
                    remote.type,
                    remote.relatedPoints,
                    remote.floor
            )

    fun fromCacheToRemote(cache: PointEntity) =
            Waypoint(
                    cache.id,
                    cache.name,
                    Point(cache.x, cache.y),
                    cache.type,
                    cache.relatedPoints,
                    cache.floor
            )
}