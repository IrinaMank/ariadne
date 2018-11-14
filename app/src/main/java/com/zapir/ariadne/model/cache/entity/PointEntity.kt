package com.zapir.ariadne.model.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters

@Entity
@TypeConverters(Converter::class)
data class PointEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val x: Float,
    val y: Float,
    val type: String,
    val relatedPoints:List<Int>
)