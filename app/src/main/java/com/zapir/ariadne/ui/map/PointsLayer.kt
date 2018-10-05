package com.zapir.ariadne.ui.map

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Point
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable



class PointsLayer(private val context: Context): MapBaseLayer(context) {

    private var points = mutableListOf<Point>()
    private val pointBtm = drawableToBitmap(context.resources.getDrawable(R.drawable.ic_point))

    fun setPoints(points: MutableList<Point>) {
        this.points = points
    }

    override fun onTouch(event: MotionEvent) {
    }

    override fun draw(canvas: Canvas, currentMatrix: Matrix, currentZoom: Float) {
        for (point in points) {
            canvas.drawBitmap(pointBtm, point.x*dp, point.y*dp, null)
        }

    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}