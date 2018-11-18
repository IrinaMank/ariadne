package com.zapir.ariadne.ui.map

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.ViewTreeObserver

class MapLayer(mapView: MapView): MapBaseLayer(mapView) {

    private var image: Picture? = null
    private var hasMeasured: Boolean = false
    override fun onTouch(event: MotionEvent) {}

    override fun draw(canvas: Canvas, currentMatrix: Matrix, currentZoom: Float, scale: Float) {
        canvas.save()
        canvas.matrix = currentMatrix
        if (image != null) {
            canvas.drawPicture(image)
        }
        canvas.restore()
    }

    private fun getInitZoom(viewWidth: Float, viewHeight: Float, imageWidth: Float,
                            imageHeight: Float): Float {
        val widthRatio = viewWidth / imageWidth
        val heightRatio = viewHeight / imageHeight

        Log.i("dg", "widthRatio:$widthRatio")
        Log.i("dg", "widthRatio:$heightRatio")

        if (widthRatio * imageHeight <= viewHeight) {
            return widthRatio
        } else if (heightRatio * imageWidth <= viewWidth) {
            return heightRatio
        }
        return 0f
    }

    private fun initMapLayer() {
        val zoom = getInitZoom(mapView.width.toFloat().toFloat(), mapView.height.toFloat(), image!!
                .getWidth()
                .toFloat(),
                image
                !!.height.toFloat())
        Log.i("lfj", java.lang.Float.toString(zoom))
        mapView.setCurrentZoom(zoom, 0f, 0f)

        val width = mapView.width - zoom * image!!.width
        val height = mapView.height - zoom * image!!.height

        mapView.translate(width / 2, height / 2)
    }

    fun setImage(image: Picture) {
        this.image = image

        if (mapView.width == 0) {
            val vto = mapView.viewTreeObserver
            vto.addOnPreDrawListener {
                if (!hasMeasured) {
                    initMapLayer()
                    hasMeasured = true
                }
                true
            }
        } else {
            initMapLayer()
        }
    }

}