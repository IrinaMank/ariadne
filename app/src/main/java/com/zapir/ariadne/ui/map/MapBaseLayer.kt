package com.zapir.ariadne.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.util.TypedValue
import android.view.MotionEvent

abstract class MapBaseLayer(
        protected val mapView: MapView
) {
    // map layer level
    protected val MAP_LEVEL = 0
    // location layer level
    protected val LOCATION_LEVEL = Integer.MAX_VALUE

    // layer show level
    var level: Int = 0
    // layer is/not show
    var isVisible = true

    /**
     * touch event
     *
     * @param event
     */
    abstract fun onTouch(event: MotionEvent)

    /**
     * draw event
     *
     * @param canvas
     * @param currentMatrix
     * @param currentZoom
     * @param currentRotateDegrees
     */
    abstract fun draw(canvas: Canvas, currentMatrix: Matrix, currentZoom: Float,
                      currentRotateDegrees: Float)

    fun changeLevel(level: Int) {
        this.level = level
    }

    protected fun setValue(value: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mapView.getResources()
                .getDisplayMetrics())
    }
}