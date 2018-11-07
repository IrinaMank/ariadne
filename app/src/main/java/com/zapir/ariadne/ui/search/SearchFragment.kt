package com.zapir.ariadne.ui.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zapir.ariadne.R
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.presenter.search.SearchPresenter
import com.zapir.ariadne.presenter.search.SearchView
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.ui.list.PointsAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import android.text.Editable
import android.text.TextWatcher
import com.zapir.ariadne.ui.main.MainActivity
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater

class SearchFragment: BaseFragment(), SearchView {

    override fun showPoints(list: List<Point>) {
        adapter.setData(list)
    }

    override val layoutRes: Int
        get() = R.layout.fragment_search

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter(): SearchPresenter {
        return SearchPresenter()
    }

    val adapter: PointsAdapter by lazy { PointsAdapter { presenter.choosePoint(it) } }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
}