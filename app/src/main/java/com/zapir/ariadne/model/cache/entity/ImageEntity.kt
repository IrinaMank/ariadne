package com.zapir.ariadne.model.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters

@Entity
@TypeConverters(Converter::class)
data class ImageEntity(
        @PrimaryKey
        val id: Int,
        val image: String
)