package com.zapir.ariadne.model.entity

import android.os.Parcelable
import com.zapir.ariadne.model.entity.common.Point
import com.zapir.ariadne.model.entity.common.PointType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Waypoint(
        val id: Int,
        val name: String? = null,
        val coordinates: Point = Point(),
        val type: String = PointType.LADDER.value,
        val relatedPoints:List<Int> = emptyList()
): Parcelable