package com.zapir.ariadne.model.entity.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FloorPicture(
        val floorId: Int = 0,
        val image: String = ""
): Parcelable

