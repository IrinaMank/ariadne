package com.zapir.ariadne.model.cache.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.zapir.ariadne.model.cache.entity.ImageEntity
import com.zapir.ariadne.model.cache.entity.PointEntity
import com.zapir.ariadne.model.cache.expired.CacheEntity
import io.reactivex.Single

@Dao
abstract class ImageDao {

    @Query("SELECT * FROM ImageEntity")
    protected abstract fun getImages(): Single<List<ImageEntity>>

    @Query("SELECT * FROM ImageEntity WHERE id= :floorId")
    protected abstract fun getImageById(floorId: Int): Single<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: List<ImageEntity>): List<Long>

    fun getImageUrls() = getImages()
    fun getUrlById(floorId: Int) = getImageById(floorId)

}