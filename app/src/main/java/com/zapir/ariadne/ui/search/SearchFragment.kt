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
import com.zapir.ariadne.presenter.search.SearchViewModel
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getPoints()
        points_rv.layoutManager = LinearLayoutManager(context)
        points_rv.adapter = this@SearchFragment.adapter
        viewModel.points.observe(this, Observer { list ->
            list?.let { adapter.setData(it) }
        })

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