package com.zapir.ariadne.ui.map

import android.graphics.*
import android.view.MotionEvent
import com.zapir.ariadne.model.entity.Waypoint

class RouteLayer(
        mapView: MapView,
        private val nodeList: List<Waypoint>
): MapBaseLayer(mapView) {

    private var routeWidth: Float = 10f // the width of route

    private var routeStartBmp: Bitmap? = null
    private var routeEndBmp: Bitmap? = null
    private var paint: Paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = routeWidth
    }
    override fun onTouch(event: MotionEvent) {
    }

    override fun draw(canvas: Canvas, currentMatrix: Matrix, currentZoom: Float, currentRotateDegrees: Float) {
        canvas.save()
        for (i in 0 until nodeList.size-1) {
            val from = nodeList[i]
            val to = nodeList[i+1]
            canvas.drawLine(from.coordinates.x, from.coordinates.y, to.coordinates.x, to
                    .coordinates.y, paint)

        }
        canvas.restore()
    }

}