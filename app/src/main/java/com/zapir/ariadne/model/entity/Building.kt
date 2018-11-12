package com.zapir.ariadne.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Building(
        val points: List<Waypoint>
): Parcelable