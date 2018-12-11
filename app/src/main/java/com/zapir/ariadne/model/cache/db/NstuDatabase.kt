package com.zapir.ariadne.model.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.zapir.ariadne.model.cache.base.CacheDao
import com.zapir.ariadne.model.cache.entity.ImageEntity
import com.zapir.ariadne.model.cache.entity.PointEntity
import com.zapir.ariadne.model.cache.expired.CacheEntity

@Database(entities = [CacheEntity::class, PointEntity::class, ImageEntity::class], version = 2)
abstract class NstuDatabase : RoomDatabase() {
    abstract fun pointDao(): PointDao
    abstract fun imageDao(): ImageDao
    abstract fun cashDao(): CacheDao
}