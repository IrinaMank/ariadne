//package com.zapir.ariadne.ui.map
//
//import android.content.Context
//import android.graphics.*
//import android.view.MotionEvent
//import com.zapir.ariadne.R
//import com.zapir.ariadne.model.entity.Waypoint
//import android.graphics.Bitmap
//import android.graphics.drawable.BitmapDrawable
//import android.graphics.drawable.Drawable
//
//
//
//class PointsLayer(private val context: Context): MapBaseLayer(context) {
//
//    private var points = mutableListOf<Waypoint>()
//    private val pointBtm = drawableToBitmap(context.resources.getDrawable(R.drawable.ic_point))
//
//    fun setPoints(waypoints: MutableList<Waypoint>) {
//        this.points = waypoints
//    }
//
//    override fun onTouch(event: MotionEvent) {
//    }
//
//    override fun draw(canvas: Canvas, rectF: RectF, currentMatrix: Matrix, currentZoom: Float,
//                      scale: Float) {
//        for (point in points) {
//            canvas.drawBitmap(pointBtm, rectF.left + point.coordinates.x*scale, rectF.top + point.coordinates.y*scale, null)
//        }
//        //canvas.dra
//    }
//
//    fun drawableToBitmap(drawable: Drawable): Bitmap {
//
//        if (drawable is BitmapDrawable) {
//            return drawable.bitmap
//        }
//
//        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, canvas.width, canvas.height)
//        drawable.draw(canvas)
//
//        return bitmap
//    }
//}