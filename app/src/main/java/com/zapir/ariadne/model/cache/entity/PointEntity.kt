package com.zapir.ariadne.model.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class PointEntity(
    @PrimaryKey
    val id: String,
    val name: String?,
    val x: Float,
    val y: Float
)