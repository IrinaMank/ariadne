package com.zapir.ariadne.ui.route

import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.onlylemi.mapview.library.layer.PointsLayer
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.entity.common.Point
import com.zapir.ariadne.presenter.route.RouteViewModel
import com.zapir.ariadne.presenter.search.WaypointsState
import com.zapir.ariadne.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_route.*
import kotlinx.android.synthetic.main.layout_map.*
import org.koin.android.ext.android.inject
import java.io.IOException

class RouteFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_route

    val viewModel: RouteViewModel by inject()
    val from: Waypoint? by lazy { arguments?.getParcelable("from") as? Waypoint }//ToDO: remove
    // hardcode
    val to: Waypoint? by lazy { arguments?.getParcelable("to") as? Waypoint }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.state.observe(this, Observer {
            when (it) {
                is WaypointsState.SuccessState ->
                {
                    val routeLayer = PointsLayer(mapview, it.list)
                    mapview.addLayer(routeLayer)
                }
                is WaypointsState.FailState -> {
                    showProgress(false)
                    print(it.error.message)
                }
                is WaypointsState.LoadingState -> {
                    showProgress(it.loading)
                }
            }

        })

        if (from != null && to != null) {
            viewModel.createRoute(from!!.id, to!!.id)
        }

    }

    private fun showProgress(show: Boolean) {
        if (show) {
            fullscreenProgressView.visibility = View.VISIBLE
            result_map.visibility = View.GONE
        }
        else {
            fullscreenProgressView.visibility = View.GONE
            result_map.visibility = View.VISIBLE
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