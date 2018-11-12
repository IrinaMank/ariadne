//package com.zapir.ariadne.ui.map
//
//import android.content.Context
//import android.graphics.*
//import android.util.AttributeSet
//import android.graphics.Bitmap
//import android.view.View
//import com.zapir.ariadne.model.entity.Waypoint
//
//
//class MapView(context: Context, attrs: AttributeSet): View(context, attrs) {
//    private var layers = mutableListOf<MapBaseLayer>()
//    private var mapLayer: MapLayer? = null
//
//    private val currentMatrix = Matrix()//it seems we don't need it
//    private var currentZoom = 1.0f
//    private var scale = 0.0f
//    private lateinit var image: Bitmap
//
//    fun loadMap(bitmap: Bitmap) {
//        val w = this.width
//        scale = (bitmap.width.toFloat()) / w
//        mapLayer = MapLayer(context)
//        mapLayer?.setMap(bitmap)
//        invalidate()
//    }
//
//    fun loadPoints(points: MutableList<Waypoint>) {
//        val layer = PointsLayer(context)
//        layer.setPoints(points)
//        addLayer(layer)
//    }
//
//    private fun addLayer(layer: MapBaseLayer) {
//        layers.add(layer)
//        invalidate()
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        mapLayer?.draw(Canvas(), currentMatrix, currentZoom, scale)
//        for (layer in layers) {
//            layer.draw(canvas, currentMatrix, currentZoom, scale)
//        }
//
//    }
//
//}