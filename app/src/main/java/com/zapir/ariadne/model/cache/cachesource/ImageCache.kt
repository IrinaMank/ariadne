package com.zapir.ariadne.model.cache.cachesource

import com.zapir.ariadne.model.cache.base.BaseCacheSource
import com.zapir.ariadne.model.cache.base.CacheDao
import com.zapir.ariadne.model.cache.db.ImageDao
import com.zapir.ariadne.model.cache.db.PointDao
import com.zapir.ariadne.model.cache.entity.ImageEntity
import com.zapir.ariadne.model.cache.entity.PointEntity

class ImageCache(
        private val imageDao: ImageDao,
        private val cache: CacheDao
): BaseCacheSource(cache) {
    override fun getEntityName(): String = ImageEntity::class.java.simpleName

    fun getImageUrls() = imageDao.getImageUrls()

    fun getUrlById(floorId: Int) = imageDao.getUrlById(floorId)

    fun saveRepositories(list: List<ImageEntity>) = operationWithCache(list) {
        imageDao.insert(list)
    }

}