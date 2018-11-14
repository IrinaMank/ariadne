package com.zapir.ariadne.model.entity

import android.os.Parcelable
import com.zapir.ariadne.model.entity.common.FloorPicture
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Static(
        val pictures: List<FloorPicture>
): Parcelable