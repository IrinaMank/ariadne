package com.zapir.ariadne.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.onlylemi.mapview.library.layer.PointsLayer
import com.zapir.ariadne.R
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.model.entity.common.Point
import com.zapir.ariadne.ui.findway.FindWayFragment
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.IOException

class MainFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_main

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(activity?.assets?.open("floor_2.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }


        bitmap?.let {
            mapview?.loadMap(bitmap)
        }

        val points = PointsLayer(mapview, listOf(Point(10f, 10f), Point(150f, 50f)))
        mapview.addLayer(points)

        search_btn.setOnClickListener {
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, FindWayFragment())
                    .addToBackStack(null)
                    .commit()
        }
//
//        search_fragment_btn.setOnClickListener {
//            activity!!.supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.container, FindWayFragment())
//                    .addToBackStack(null)
//                    .commit()
//        }
    }

}