package com.zapir.ariadne.model.repositories

import com.zapir.ariadne.model.cache.cachesource.ImageCache
import com.zapir.ariadne.model.cache.cachesource.PointCache
import com.zapir.ariadne.model.remote.RouterApi
import io.reactivex.Single

class ImagesRepository(
        private val api: RouterApi,
        private val cache: ImageCache
) {

    fun getImageUrl(floorId: Int): Single<String> =
            cache.isExpired().flatMap {
                if (it) {
                    api.getFloorUrls().map {
                        it.pictures.filter {
                            it.floorID == floorId
                        }.firstOrNull()
                    }.map {
                        it.image
                    }
                } else {
                    cache.getUrlById(floorId).map {
                        it.image
                    }
                }
            }
}