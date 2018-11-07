package com.zapir.ariadne.model.entity.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Point(
        val x: Float = 0.0f,
        val y: Float = 0.0f
): Parcelable

