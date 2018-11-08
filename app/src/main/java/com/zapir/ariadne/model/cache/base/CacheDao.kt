package com.zapir.ariadne.model.cache.base

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.zapir.ariadne.model.cache.expired.CacheEntity

@Dao
abstract class CacheDao {

    @Query("SELECT * FROM CacheEntity WHERE tableName = :tableName")
    abstract fun getCash(tableName: String): CacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: CacheEntity): Long
}