package com.zapir.ariadne.ui.main

import android.os.Bundle
import com.zapir.ariadne.R
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.ui.findway.FindWayFragment
import com.zapir.ariadne.ui.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_main

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        main_fragment_btn.setOnClickListener {
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commit()
        }//ToDo: remove !!

        search_fragment_btn.setOnClickListener {
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, FindWayFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }

}