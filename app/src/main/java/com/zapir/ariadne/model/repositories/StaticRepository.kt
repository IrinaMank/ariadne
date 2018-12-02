package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.cache.cachesource.PointCache
import com.zapir.ariadne.model.remote.RouterApi
import io.reactivex.Single

class StaticRepository(
        private val api: RouterApi,
        private val cache: PointCache
) {

    fun getImageUrl(floorId: Int): Single<String> =
            api.getSatic().map{
                it.pictures.filter {
                    it.floorID == floorId
                }.firstOrNull()
            }.map {
                it.image
            }
}