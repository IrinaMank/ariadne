package com.zapir.ariadne.ui.map

import android.content.Context
import android.graphics.*
import android.view.MotionEvent

class MapLayer(private val context: Context): MapBaseLayer(context) {

    private var bitmap: Bitmap? = null
    override fun onTouch(event: MotionEvent) {
    }

    override fun draw(canvas: Canvas, currentMatrix: Matrix, currentZoom: Float) {
        bitmap?.let {
            //scaleMapToScreenSize(canvas.width.toFloat())
           // canvas.drawBitmap(it, null, null)
            canvas.drawBitmap(it, null, Rect(0, 0, (it.width*dp).toInt(), (it.height*dp).toInt
            ()), null)
        }
    }

    fun setMap(bitmap: Bitmap) {
        this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
    }

//    private fun scaleMapToScreenSize(width: Float) {
//        bitmap?.let {
//            ///val coeff = width / it.width
//            //it.height = (it.height * coeff).toInt()
//            it.height = (it.height * dp).toInt()
//        }
//    }
}