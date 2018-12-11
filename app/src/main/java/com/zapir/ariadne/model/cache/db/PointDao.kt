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

    @Query("SELECT * FROM PointEntity WHERE id = :id")
    protected abstract fun getRawPoints(id: Int): Single<PointEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: List<PointEntity>): List<Long>

    fun getPoints() = getRawPoints()
    fun getPoints(id: Int) = getRawPoints(id)

}