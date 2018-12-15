package com.zapir.ariadne.ui.map

import android.graphics.*
import android.view.MotionEvent
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Waypoint
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth



class RouteLayer(
        mapView: MapView,
        private val nodeList: List<Waypoint>
): MapBaseLayer(mapView) {

    private var routeWidth: Float = 10f // the width of route

    private var routeStartBmp: Bitmap? =  mapView.context.resources.getDrawable(R.drawable
            .ic_from).drawableToBitmap()
    private var routeEndBmp: Bitmap? = mapView.context.resources.getDrawable(R.drawable
            .ic_to).drawableToBitmap()
    private var paint: Paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.color = mapView.resources.getColor(R.color.colorPrimaryDark)
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = routeWidth
    }
    override fun onTouch(event: MotionEvent) {
    }

    override fun draw(canvas: Canvas, currentMatrix: Matrix, currentZoom: Float, currentRotateDegrees: Float) {
        canvas.save()
        routeStartBmp?.let {
            canvas?.drawBitmap(it, nodeList[0].coordinates.x - it.width / 2,
                    nodeList[0].coordinates.y - it.height, null)
        }
        for (i in 0 until nodeList.size-1) {
            val from = nodeList[i]
            val to = nodeList[i+1]
            canvas.drawLine(from.coordinates.x, from.coordinates.y, to.coordinates.x, to
                    .coordinates.y, paint)

        }
        routeEndBmp?.let {
            canvas?.drawBitmap(it, nodeList[nodeList.size-1].coordinates.x - it.width / 2,
                    nodeList[nodeList.size-1].coordinates.y - it.height, null)
        }
        canvas.restore()
    }

}