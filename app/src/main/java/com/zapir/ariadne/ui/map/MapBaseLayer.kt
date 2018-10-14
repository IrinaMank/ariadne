package com.zapir.ariadne.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.util.TypedValue
import android.view.MotionEvent

abstract class MapBaseLayer(private val context: Context) {
    protected val dp = context.resources.displayMetrics.density
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
    abstract fun draw(canvas: Canvas, rect: RectF, currentMatrix: Matrix, currentZoom: Float,
                      scale:
    Float)
}