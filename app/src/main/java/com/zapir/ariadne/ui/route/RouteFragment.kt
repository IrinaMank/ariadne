package com.zapir.ariadne.ui.route

import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.onlylemi.mapview.library.layer.PointsLayer
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.presenter.main.MainState
import com.zapir.ariadne.presenter.route.RouteViewModel
import com.zapir.ariadne.presenter.search.WaypointsState
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.ui.map.DebugLayer
import kotlinx.android.synthetic.main.fragment_route.*
import kotlinx.android.synthetic.main.layout_map.*
import org.koin.android.ext.android.inject

class RouteFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_route

    val viewModel: RouteViewModel by inject()
    val from: Waypoint? by lazy { arguments?.getParcelable("from") as? Waypoint }//ToDO: remove
    // hardcode
    val to: Waypoint? by lazy { arguments?.getParcelable("to") as? Waypoint }


    var currPoint = Waypoint(4)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        floor_btns.clearCheck()
        setCheck()
        floor_btns.setOnCheckedChangeListener{ radioGroup, i ->
            when(i) {
                R.id.first_floor -> viewModel.currentFloor = 0
                R.id.second_floor -> viewModel.currentFloor = 1
                R.id.third_floor -> viewModel.currentFloor = 2
                R.id.forth_floor -> viewModel.currentFloor = 3
                R.id.fifth_floor -> viewModel.currentFloor = 4
            }
        }

        viewModel.stateImages.observe(this, Observer { state ->
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
                                    val pointLayer = PointsLayer(mapview, listOf())
                                    mapview.addAndClearLayer(pointLayer)
                                    viewModel.pointFromFloor(viewModel.currentFloor)
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


//         if (from != null && to != null) {
//            viewModel.createRoute(from!!, to!!)
//        }

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is WaypointsState.SuccessState ->
                {
                    showProgress(false)

                    for (item in state.list) {
                        currPoint = item
//                        val pointLayer = PointsLayer(mapview, listOf(item))
//                        mapview.addLayer(pointLayer)
                        viewModel.debugConn(currPoint, item.id)
                    }

                }
                is WaypointsState.FailState -> {
                    showProgress(false)
                    print(state.error.message)
                }
                is WaypointsState.LoadingState -> {
                    showProgress(state.loading)
                }
            }
        })

        viewModel.stateCon.subscribe {  state ->
            when (state) {
                is WaypointsState.SuccessState ->
                {
                    showProgress(false)
                    val pointLayer = DebugLayer(mapview, state.list.last(), state.list)
                    mapview.addLayer(pointLayer)
                }
                is WaypointsState.FailState -> {
                    showProgress(false)
                    print(state.error.message)
                }
                is WaypointsState.LoadingState -> {
                    showProgress(state.loading)
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFloorUrl(0)
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            fullscreenProgressView.visibility = View.VISIBLE
        }
        else {
            fullscreenProgressView.visibility = View.GONE
        }
    }

    private fun setCheck() {
        when(viewModel.currentFloor) {
            0 -> first_floor.isChecked = true
            1 -> second_floor.isChecked = true
            2 -> third_floor.isChecked = true
            3 -> forth_floor.isChecked = true
            4 -> fifth_floor.isChecked = true
        }
    }


    companion object {
        fun startIntent(from: Waypoint?, to: Waypoint?): BaseFragment {
            val bundle = Bundle()
            bundle.putParcelable("from", from)
            bundle.putParcelable("to", to)
            val fragment = RouteFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}