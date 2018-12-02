package com.zapir.ariadne.ui.main

import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.onlylemi.mapview.library.layer.PointsLayer
import com.zapir.ariadne.R
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.model.entity.common.Point
import com.zapir.ariadne.presenter.main.MainState
import com.zapir.ariadne.presenter.main.MainViewModel
import com.zapir.ariadne.presenter.search.WaypointsState
import com.zapir.ariadne.ui.findway.FindWayFragment
import com.zapir.ariadne.ui.map.MapLayer
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_map.*
import org.koin.android.ext.android.inject
import java.io.IOException

class MainFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_main

    private val viewModel: MainViewModel by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        floor_btns.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.first_floor -> viewModel.currentFloor = 0
                R.id.second_floor -> viewModel.currentFloor = 1
            }
        }
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is MainState.SuccessState ->
                {
                    showProgress(false)
                    val bm = Glide.with(this)
                            .asBitmap()
                            .load(state.currentFloor)
                            .into(object  : SimpleTarget<Bitmap>() {
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    mapview.loadMap(resource)
                                }
                            })

                }
                is MainState.FailState -> {
                    showProgress(false)
                    print(state.error.message)
                }
                is MainState.LoadingState -> {
                    showProgress(state.loading)
                }
            }
        })

        search_btn.setOnClickListener {
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, FindWayFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            fullscreenProgressView.visibility = View.VISIBLE
        }
        else {
            fullscreenProgressView.visibility = View.GONE
        }
    }

}