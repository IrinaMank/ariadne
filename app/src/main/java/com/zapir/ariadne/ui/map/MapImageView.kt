package com.zapir.ariadne.ui.map

import android.R.attr.centerY
import android.R.attr.centerX
import android.content.Context
import android.view.View.MeasureSpec
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import android.icu.lang.UScript.getShortName
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.support.v4.view.ViewCompat.setLayerType
import android.os.Build
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.support.v7.widget.AppCompatImageView
import android.view.View
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Point
import javax.sql.DataSource


class MapImageView : AppCompatImageView {
    private var layers = mutableListOf<MapBaseLayer>()
    /*
    * Path of them image to be clipped (to be shown)
    * */
    internal lateinit var clipPath: Path

    /*
    * Place holder drawable (with background color and initials)
    * */
    internal lateinit var drawable: Drawable
    private lateinit var bitmap: Bitmap

    /*
    * Image width and height (both are same and constant, defined in dimens.xml
    * We cache them in this field
    * */
    private var imageSize: Int = 0

    /*
    * Bounds of the canvas in float
    * Used to set bounds of member initial and background
    * */
    internal lateinit var rectF: RectF
    private var originalWidth = 0.0f
    private var scale = 0.0f

    private lateinit var url: String

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    /*
    * Initialize fields
    * */
    protected fun init() {

        /*
        * Below Jelly Bean, clipPath on canvas would not work because lack of hardware acceleration
        * support. Hence, we should explicitly say to use software acceleration.
        * */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        clipPath = Path()

        imageSize = resources.getDimensionPixelSize(R.dimen.map_size)
    }

    fun setImage(url: String) {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            originalWidth = resource.width.toFloat()
                            scale = originalWidth / this@MapImageView.width
                            bitmap = resource
                            this@MapImageView.setImageBitmap(resource)
                        }
                    })
    }

    /*
    * Set the canvas bounds here
    * */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val screenWidth = width
        val screenHeight = height
//        rectF.set(this.left.toFloat(), top.toFloat(), screenWidth.toFloat(), screenHeight
          //      .toFloat())
    }

    override fun onSizeChanged(xNew: Int, yNew: Int, xOld: Int, yOld: Int) {
        super.onSizeChanged(xNew, yNew, xOld, yOld)
        rectF = RectF(left.toFloat(), top.toFloat(), width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (layer in layers) {
            layer.draw(canvas, rectF, Matrix(), 1f, 1f)
        }
    }

    fun loadPoints(points: MutableList<Point>) {
        val layer = PointsLayer(context)
        layer.setPoints(points)
        addLayer(layer)
    }

    private fun addLayer(layer: MapBaseLayer) {
        layers.add(layer)
        invalidate()
    }
}