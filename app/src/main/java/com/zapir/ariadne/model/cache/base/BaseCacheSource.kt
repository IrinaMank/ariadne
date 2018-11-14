package com.zapir.ariadne.model.cache.base

import com.zapir.ariadne.model.cache.expired.CacheEntity
import io.reactivex.Single

abstract class BaseCacheSource(
    private val cashDao: CacheDao
) {

    abstract fun getEntityName(): String

    fun isExpired(): Single<Boolean> =
        cashDao.getCash(getEntityName())
                .flatMap {
                        val currentTime = System.currentTimeMillis()
                        val lastUpdateTime = it.expirationTime
                        Single.just(currentTime - lastUpdateTime > 60 * 60 * 1000)
                }
                .onErrorReturnItem(true)

    protected fun <T> operationWithCache(entity: T, operation: (T) -> Unit) {
        updateCash()
        operation(entity)
    }

    private fun updateCash() {
        val cache = CacheEntity(getEntityName(), createExpirationTime())
        cashDao.insert(cache)
    }

    private fun createExpirationTime() = System.currentTimeMillis() +  60 * 60 * 1000

}
