package com.zapir.ariadne.model.cache.base

import com.zapir.ariadne.model.cache.expired.CacheEntity

abstract class BaseCacheSource(
    private val cashDao: CacheDao
) {

    abstract fun getEntityName(): String

    fun isExpired(): Boolean {
        val cash = cashDao.getCash(getEntityName())
        return if (cash == null) {
            true
        } else {
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = cash.expirationTime
            currentTime - lastUpdateTime > 60 * 60 * 1000
        }
    }

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
