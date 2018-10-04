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

        search_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                adapter.filter.filter(s)
            }
        })
    }

}