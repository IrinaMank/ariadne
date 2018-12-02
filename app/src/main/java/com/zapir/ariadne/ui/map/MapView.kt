package com.zapir.ariadne.ui.map

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.*
import android.view.MotionEvent.INVALID_POINTER_ID
import android.widget.ImageView
import java.util.*

class MapView(context: Context, attributeSet: AttributeSet) : ImageView(context, attributeSet) {

    private var layers: MutableList<MapBaseLayer> = mutableListOf()

    private var offsetX = 0f
    private var offsetY = 0f

    private var minZoom = 0.15f
    private var maxZoom = minZoom * 2f

    //private var margin = context.resources.getDimension(R.dimen.map_margin)

    private var mLastTouchX: Float = 0f
    private var mLastTouchY: Float = 0f
    private val currentMatrix = Matrix()
    private var currentRotateDegrees = 0.0f

    private var mActivePointerId = INVALID_POINTER_ID

    private var mPosX: Float = 0f
    private var mPosY: Float = 0f

    private var image: Picture? = null

    private var scaleFactor = 1f
    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor

            scaleFactor = Math.max(minZoom, Math.min(scaleFactor, maxZoom))

            invalidate()
            return true
        }
    }
    private val tapListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            if (scaleFactor > minZoom) {
                scaleFactor = minZoom
            } else {
                scaleFactor *= 2f
            }
            invalidate()
            return super.onDoubleTap(e)
        }
    }

    private val mScaleDetector = ScaleGestureDetector(context, scaleListener)
    private val tapDetector = GestureDetector(context, tapListener)

    init {
        layers = object : ArrayList<MapBaseLayer>() {
            override fun add(element: MapBaseLayer): Boolean {
                if (layers.size != 0) {
                    if (element.level >= this[this.size - 1].level) {
                        super.add(element)
                    } else {
                        for (i in layers.indices) {
                            if (element.level < this[i].level) {
                                super.add(i, element)
                                break
                            }
                        }
                    }
                } else {
                    super.add(element)
                }
                return true
            }
        }
        setWillNotDraw(false)
    }

    fun loadMap(bitmap: Bitmap) {
        loadMap(getPictureFromBitmap(bitmap))
    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        initZoom()
//    }

    private fun loadMap(picture: Picture?) {
        Thread(Runnable {
            if (picture != null) {
                image = picture
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
        pivotY = image?.height?.div(2f) ?: 0f

        canvas.translate(mPosX, mPosY)
        canvas.scale(scaleFactor, scaleFactor)
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
        tapDetector.onTouchEvent(ev)

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

                    mPosX += dx
                    mPosY += dy

                    getOffset()
                    mPosX = if (mPosX < 0 && offsetX < 0) {
                        maxOf(mPosX, offsetX)
                    } else {
                        0f
                    }
                    mPosY = if (mPosY < 0 && offsetY < 0) {
                        maxOf(mPosY, offsetY)
                    } else {
                        0f
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
        //val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
       // val display = wm.defaultDisplay
        val screenSize = Point()
        screenSize.x = (this as View).measuredWidth
        screenSize.y = (this as View).measuredHeight

//        val px = TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//        margin,
//        resources.displayMetrics
//        )
//        display.getSize(screenSize)
//        screenSize.x -= px.toInt()
//        screenSize.y -= px.toInt()
        image?.let {

            scaleFactor = screenSize.x.toFloat().div(it.width)
            minZoom = scaleFactor
            maxZoom = scaleFactor * 4f
        }
    }
}