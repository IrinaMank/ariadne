package com.zapir.ariadne.ui.findway

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.zapir.ariadne.R
import com.zapir.ariadne.presenter.findway.FindWayViewModel
import com.zapir.ariadne.presenter.search.SearchViewModel
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.ui.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_findway.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FindWayFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_findway

    val viewModel: FindWayViewModel by inject()
    val pointsViewModel: SearchViewModel by sharedViewModel<SearchViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        textview.setOnClickListener {
            activity?.supportFragmentManager
                    ?.beginTransaction()?.replace(R.id.container,
            SearchFragment())?.addToBackStack(null)?.commit()
        }

        textview.setText(pointsViewModel.from.value?.name ?: "PKP")

        pointsViewModel.from.observe(this, Observer {
            textview.setText(it?.name)
            if (it == null) {
                textview.setText("NULL")
            }
        })
    }
}