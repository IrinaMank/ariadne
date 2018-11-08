package com.zapir.ariadne.ui.route

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Parcelable
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.presenter.route.RouteViewModel
import com.zapir.ariadne.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_findway.*
import kotlinx.android.synthetic.main.fragment_route.*
import org.koin.android.ext.android.inject

class RouteFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_route

    val viewModel: RouteViewModel by inject()
    val from: Point? by lazy { arguments?.getParcelable("from") as Point }
    val to: Point? by lazy { arguments?.getParcelable("to") as Point }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.route.observe(this, Observer { text.text = it.toString() })

        if (from != null && to != null) {
            viewModel.createRoute(from!!, to!!)
        }

    }

    companion object {
        fun startIntent(from: Point?, to: Point?): BaseFragment {
            val bundle = Bundle()
            bundle.putParcelable("from", from)
            bundle.putParcelable("to", to)
            val fragment = RouteFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}