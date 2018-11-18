package com.zapir.ariadne.ui.map

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.graphics.Bitmap
import android.view.*
import java.util.ArrayList

class MapView(context: Context, atributeSet: AttributeSet) : SurfaceView(context, atributeSet),
        SurfaceHolder
.Callback {

    private var holders: SurfaceHolder? = null
    private var isMapLoadFinish = false
    private var layers: MutableList<MapBaseLayer> = mutableListOf()// all layers
    private var mapLayer: MapLayer? = null

    private var canvas: Canvas? = null

    private var minZoom = 0.5f
    private var maxZoom = 3.0f

    private val startTouch = PointF()
    private var mid = PointF()

    private val saveMatrix = Matrix()
    private val currentMatrix = Matrix()
    private var currentZoom = 1.0f
    private var saveZoom = 0f
    private var currentRotateDegrees = 0.0f
    private var saveRotateDegrees = 0.0f

    private val TOUCH_STATE_NO = 0 // no touch
    private val TOUCH_STATE_SCROLL = 1 // scroll(one point)
    private val TOUCH_STATE_SCALE = 2 // scale(two points)
    private var currentTouchState = TOUCH_STATE_SCALE // default touch state

    private var oldDist = 0f
    private var oldDegree = 0f
    private var isScaleAndRotateTogether = false

    private var scaleFactor = 1f
    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor

            scaleFactor = Math.max(minZoom, Math.min(scaleFactor, maxZoom))

            invalidate()
            return true
        }
    }

    private val mScaleDetector = ScaleGestureDetector(context, scaleListener)

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        this.holders = holder
        setBackground()
    }

    init {
        holder.addCallback(this)

        layers = object : ArrayList<MapBaseLayer>() {
            override fun add(layer: MapBaseLayer): Boolean {
                if (layers.size != 0) {
                    if (layer.level >= this[this.size - 1].level) {
                        super.add(layer)
                    } else {
                        for (i in layers.indices) {
                            if (layer.level < this[i].level) {
                                super.add(i, layer)
                                break
                            }
                        }
                    }
                } else {
                    super.add(layer)
                }
                return true
            }
        }
        setWillNotDraw(false)
    }

    fun loadMap(bitmap: Bitmap) {
        loadMap(getPictureFromBitmap(bitmap))
    }

    var image: Picture? = null
    fun loadMap(picture: Picture?) {
        isMapLoadFinish = false

        Thread(Runnable {
            if (picture != null) {
                if (mapLayer == null) {
                    mapLayer = MapLayer(this@MapView)
                    // add map image layer
                    //layers.add(mapLayer!!)
                }
                image = picture
                mapLayer?.setImage(picture)
                isMapLoadFinish = true
                invalidate()
            }
        }).start()
    }


    fun getPictureFromBitmap(bitmap: Bitmap): Picture {
        val picture = Picture()
        val canvas = picture.beginRecording(bitmap.width,
                bitmap.height)
        canvas.drawBitmap(
                bitmap, null,
                RectF(0f, 0f, bitmap.width.toFloat(), bitmap
                        .height.toFloat()), null)
        picture.endRecording()
        return picture
    }

    fun setBackground() {
        if (holder != null) {
            canvas = holder.lockCanvas()
            if (canvas != null) {
                canvas!!.drawColor(-1)
                holder.unlockCanvasAndPost(canvas)
            }
        }
    }

    fun getCurrentZoom(): Float {
        return currentZoom
    }

    fun setCurrentZoom(zoom: Float) {
        setCurrentZoom(zoom, (width / 2).toFloat(), (height / 2).toFloat())
    }

    fun translate(x: Float, y: Float) {
        currentMatrix.postTranslate(x, y)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.scale(scaleFactor, scaleFactor, mid.x, mid.y)
        if (image != null) {
            canvas.drawPicture(image)
        }
        layers.forEach {
            it.draw(canvas, currentMatrix, scaleFactor, currentRotateDegrees)
        }
        canvas.restore()
    }

    fun setCurrentZoom(zoom: Float, x: Float, y: Float) {
        currentMatrix.postScale(zoom / this.currentZoom, zoom / this.currentZoom, x, y)
        this.currentZoom = zoom
    }

    fun convertMapXYToScreenXY(x: Float, y: Float): FloatArray {
        val invertMatrix = Matrix()
        val value = floatArrayOf(x, y)
        currentMatrix.invert(invertMatrix)
        invertMatrix.mapPoints(value)
        return value
    }

    fun addLayer(layer: MapBaseLayer) {
        layers.add(layer)
        invalidate()
    }

    private fun distance(event: MotionEvent, mid: PointF): Float {
        return getDistanceBetweenTwoPoints(event.getX(0), event.getY(0), mid.x, mid.y)
    }

    fun getDistanceBetweenTwoPoints(x1: Float, y1: Float,
                                    x2: Float, y2: Float): Float {
        return Math.sqrt(Math.pow((x2 - x1).toDouble(), 2.0) + Math.pow((y2 - y1).toDouble(), 2.0)).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                saveMatrix.set(currentMatrix)
                startTouch.set(event.getX(), event.getY())
                currentTouchState = TOUCH_STATE_SCROLL
            }
            MotionEvent.ACTION_POINTER_DOWN ->
                if (event.pointerCount == 2) {
                    saveMatrix.set(currentMatrix)
                    saveZoom = currentZoom
                    saveRotateDegrees = currentRotateDegrees
                    startTouch.set(event.getX(0), event.getY(0))
                    mid = midPoint(event)
                    oldDist = distance(event, mid)
                }
            MotionEvent.ACTION_MOVE -> {
                if (currentTouchState == TOUCH_STATE_SCROLL) {
                    saveMatrix.set(currentMatrix)
                    currentMatrix.postTranslate(event.x - startTouch.x, event.y - startTouch.y)
                    refreshMap()
                }
            }
        }
                if (!isMapLoadFinish) {
                    return false
                }
            mScaleDetector.onTouchEvent(event)
                return true
        }

    private fun refreshMap() {
        canvas?.drawPicture(image)
    }

    private fun midPoint(event: MotionEvent): PointF {
        return getMidPointBetweenTwoPoints(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
    }

    fun getMidPointBetweenTwoPoints(x1: Float, y1: Float, x2: Float, y2: Float): PointF {
        return PointF((x1 + x2) / 2, (y1 + y2) / 2)
    }
}