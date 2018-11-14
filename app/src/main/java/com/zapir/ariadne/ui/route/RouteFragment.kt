package com.zapir.ariadne.ui.route

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Parcelable
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.presenter.route.RouteViewModel
import com.zapir.ariadne.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_findway.*
import kotlinx.android.synthetic.main.fragment_route.*
import org.koin.android.ext.android.inject

class RouteFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_route

    val viewModel: RouteViewModel by inject()
    val from: Waypoint? by lazy { arguments?.getParcelable("from") as Waypoint }//ToDO: remove hardcode
    val to: Waypoint? by lazy { arguments?.getParcelable("to") as Waypoint }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.route.observe(this, Observer { text.text = it.toString() })

        if (from != null && to != null) {
            viewModel.createRoute(from!!, to!!)
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