package com.zapir.ariadne.model.cache.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.zapir.ariadne.model.cache.entity.PointEntity
import com.zapir.ariadne.model.cache.expired.CacheEntity
import io.reactivex.Single

@Dao
abstract class PointDao {

    @Query("SELECT * FROM PointEntity")
    protected abstract fun getRawPoints(): Single<List<PointEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: List<PointEntity>): List<Long>

    fun getPoints() = getRawPoints()

}