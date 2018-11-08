package com.zapir.ariadne.model.cache.expired

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class CacheEntity(
    @PrimaryKey
    val tableName: String,
    val expirationTime: Long
)
