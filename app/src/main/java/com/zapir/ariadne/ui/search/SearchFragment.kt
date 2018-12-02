package com.zapir.ariadne.ui.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.ui.search.list.PointsAdapter
import android.text.Editable
import android.text.TextWatcher
import android.arch.lifecycle.Observer
import android.util.Log
import android.view.View
import com.zapir.ariadne.presenter.search.SearchViewModel
import com.zapir.ariadne.presenter.search.WaypointsState
import com.zapir.ariadne.ui.route.RouteFragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment: BaseFragment() {

    override val layoutRes: Int
        get() = R.layout.fragment_search

    private val viewModel: SearchViewModel by sharedViewModel()
    private val direction: String? by lazy { arguments?.getString("direction") }//ToDo: remove
    // hardcode

    val adapter: PointsAdapter by lazy { PointsAdapter {
        if (direction == "from") {
            viewModel.chooseFromPoint(it)
        } else {
            viewModel.chooseToPoint(it)
        }
        activity?.supportFragmentManager?.popBackStack()}  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state.observe(this, Observer { state ->
            Log.e("dlf", "${state!!::class}")
            when (state) {
                is WaypointsState.SuccessState ->
                {
                    showProgress(false)
                    adapter.setData(state.list)
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

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getPoints()
        points_rv.layoutManager = LinearLayoutManager(context)
        points_rv.adapter = this@SearchFragment.adapter

        search_et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.filter.filter(p0)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            Log.e("dlf", "VISIB;E")
            fullscreenProgressView.visibility = View.VISIBLE
        }
        else {
            Log.e("dlf", "GONE")
            fullscreenProgressView.visibility = View.GONE
        }
    }

    companion object {
        fun startIntent(direction: String): BaseFragment {
            val bundle = Bundle()
            bundle.putString("direction", direction)
            val fragment = SearchFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}