package com.zapir.ariadne.model.cache.cachesource

import com.zapir.ariadne.model.cache.base.BaseCacheSource
import com.zapir.ariadne.model.cache.base.CacheDao
import com.zapir.ariadne.model.cache.db.PointDao
import com.zapir.ariadne.model.cache.entity.PointEntity

class PointCache(
        private val pointDao: PointDao,
        private val cache: CacheDao
): BaseCacheSource(cache) {
    override fun getEntityName(): String = PointEntity::class.java.simpleName

    fun getPoints() = pointDao.getPoints()
    fun getPoints(id: Int) = pointDao.getPoints(id)

    fun saveRepositories(list: List<PointEntity>) = operationWithCache(list) {
        pointDao.insert(list)
    }

}