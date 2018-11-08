package com.zapir.ariadne.ui.route

import android.os.Bundle
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.presenter.route.RouteViewModel
import com.zapir.ariadne.ui.base.BaseFragment
import org.koin.android.ext.android.inject

class RouteFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_route

    val viewModel: RouteViewModel by inject()

    companion object {
        fun startIntent(from: String, to: String): BaseFragment {
            val bundle = Bundle()
            bundle.putString("from", from)
            bundle.putString("to", to)
            val fragment = RouteFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}