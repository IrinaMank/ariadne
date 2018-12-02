package com.onlylemi.mapview.library.layer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.view.MotionEvent
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.common.Point
import com.zapir.ariadne.ui.map.MapBaseLayer
import com.zapir.ariadne.ui.map.MapView
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

class PointsLayer(mapView: MapView, var marks: List<Point>? = null) :
        MapBaseLayer(mapView) {
    private var listener: MarkIsClickListener? = null

    private var bmpMark: Bitmap? = null
    private var bmpMarkTouch: Bitmap? = null

    private var radiusMark: Float = 30.toFloat()
    var isClickMark = false
        private set
    var num = -1

    private var paint: Paint? = null

    init {

        initLayer()
    }

    private fun initLayer() {
        radiusMark = setValue(10f)

        bmpMark = drawableToBitmap(mapView.context.resources.getDrawable(R.drawable.ic_point))
        //bmpMarkTouch = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.mark_touch)

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.FILL_AND_STROKE
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        var bitmap: Bitmap? = null

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap!!)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun onTouch(event: MotionEvent) {
//        if (marks != null) {
//            if (!marks!!.isEmpty()) {
//                val goal = mapView.convertMapXYToScreenXY(event.x, event.y)
//                for (i in marks!!.indices) {
//                    if (MapMath.getDistanceBetweenTwoPoints(goal[0], goal[1],
//                                    marks!![i].x - bmpMark!!.width / 2, marks!![i].y - bmpMark!!
//                                    .height / 2) <= 50) {
//                        num = i
//                        isClickMark = true
//                        break
//                    }
//
//                    if (i == marks!!.size - 1) {
//                        isClickMark = false
//                    }
//                }
//            }
//
//            if (listener != null && isClickMark) {
//                listener!!.markIsClick(num)
//                mapView.setBackground()
//            }
//        }
    }

    override fun draw(canvas: Canvas, currentMatrix: Matrix, currentZoom: Float,
    currentRotateDegrees: Float) {
        if (isVisible && marks != null) {
            canvas.save()
            if (!marks!!.isEmpty()) {
                for (i in marks!!.indices) {
                    val mark = marks!![i]
                    val goal = floatArrayOf(mark.x, mark.y)
                    //currentMatrix.mapPoints(goal)
                    //mark ico
                    bmpMark?.let {
                        canvas?.drawBitmap(it, goal[0] - it.width / 2,
                                goal[1] - it.height / 2, null)
                    }
                }
            }
            canvas.restore()
        }
    }

    fun setMarkIsClickListener(listener: MarkIsClickListener) {
        this.listener = listener
    }

    interface MarkIsClickListener {
        fun markIsClick(num: Int)
    }
}
