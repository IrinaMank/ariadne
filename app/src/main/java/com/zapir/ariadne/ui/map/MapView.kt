package com.zapir.ariadne.ui.map

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.ScaleGestureDetector
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import java.util.*
import kotlin.math.abs

class MapView(context: Context, atributeSet: AttributeSet) : ImageView(context, atributeSet) {

    private var isMapLoadFinish = false
    private var layers: MutableList<MapBaseLayer> = mutableListOf()// all layers

    var offsetX = 0f
    var offsetY = 0f

    private var minZoom = 0.15f
    private var maxZoom = 0.8f

    private var mid = PointF()
    private var mLastTouchX: Float = 0.toFloat()
    private var mLastTouchY: Float = 0.toFloat()
    private val currentMatrix = Matrix()
    private var currentRotateDegrees = 0.0f

    private var mActivePointerId = INVALID_POINTER_ID

    private var mPosX: Float = 0.toFloat()
    private var mPosY: Float = 0.toFloat()

    private var image: Picture? = null

    private var scaleFactor = 1f
    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor

            scaleFactor = Math.max(minZoom, Math.min(scaleFactor, maxZoom))

            mid.x *= scaleFactor
            mid.y *= scaleFactor
            invalidate()
            return true
        }
    }

    private val mScaleDetector = ScaleGestureDetector(context, scaleListener)

    init {
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


    private fun loadMap(picture: Picture?) {
        isMapLoadFinish = false

        Thread(Runnable {
            if (picture != null) {
                image = picture
                isMapLoadFinish = true
                initZoom()
                invalidate()
            }
        }).start()
    }


    private fun getPictureFromBitmap(bitmap: Bitmap): Picture {
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        pivotX = image?.width?.div(2f) ?: 0f
        pivotY = image?.height?.div(2f)?: 0f
        pivotY += (this as View).height.div(2f)


        canvas.translate(mPosX, mPosY)
        canvas.scale(scaleFactor, scaleFactor, pivotX, pivotY)
        if (image != null) {
            canvas.drawPicture(image)
        }
        layers.forEach {
            it.draw(canvas, currentMatrix, scaleFactor, currentRotateDegrees)
        }
        canvas.restore()
    }

    fun addLayer(layer: MapBaseLayer) {
        layers.add(layer)
        invalidate()
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        mScaleDetector.onTouchEvent(ev)

        val action = ev.getAction()
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val x = ev.x
                val y = ev.y

                mLastTouchX = x
                mLastTouchY = y
                mActivePointerId = ev.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = ev.findPointerIndex(mActivePointerId)
                val x = ev.getX(pointerIndex)
                val y = ev.getY(pointerIndex)

                if (!mScaleDetector.isInProgress) {
                    val dx = x - mLastTouchX
                    val dy = y - mLastTouchY

                    getOffset()
                    mPosX += dx
                    mPosY += dy
                    mPosX = if (mPosX < 0) {
                        -minOf(abs(mPosX), abs(offsetX))

                    } else {
                        0f
                    }
                    mPosY = if (mPosY >= 0) {
                        0f
                    } else {
                        -minOf(abs(mPosY), abs(offsetY))
                    }

                    invalidate()
                }

                mLastTouchX = x
                mLastTouchY = y
            }

            MotionEvent.ACTION_UP -> {
                mActivePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_CANCEL -> {
                mActivePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = ev.action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
                val pointerId = ev.getPointerId(pointerIndex)
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mLastTouchX = ev.getX(newPointerIndex)
                    mLastTouchY = ev.getY(newPointerIndex)
                    mActivePointerId = ev.getPointerId(newPointerIndex)
                }
            }
        }
        return true
    }


    private fun getOffset() {
        image?.let {
            offsetX = ((this as View).width.toFloat() - it.width * scaleFactor)
            offsetY = ((this as View).height.toFloat()) - it.height * scaleFactor
        }
    }

    private fun initZoom() {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val screenSize = Point()
        display.getSize(screenSize)
        image?.let {

            scaleFactor = screenSize.x.toFloat().div(it.width)
            minZoom = scaleFactor
        }
    }
}