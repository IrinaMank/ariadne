package com.zapir.ariadne.ui.main

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zapir.ariadne.R
import com.zapir.ariadne.presenter.main.MainPresenter
import com.zapir.ariadne.presenter.main.MainView
import com.zapir.ariadne.presenter.search.SearchPresenter
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: BaseFragment(), MainView {
    override val layoutRes: Int
        get() = R.layout.fragment_main

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        main_fragment_btn.setOnClickListener {
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commitNow()
        }

        search_fragment_btn.setOnClickListener {
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SearchFragment())
                    .commitNow()
        }
    }

}