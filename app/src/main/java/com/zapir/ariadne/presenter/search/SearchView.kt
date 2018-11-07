package com.zapir.ariadne.presenter.search

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.zapir.ariadne.model.entity.Waypoint

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchView : MvpView {
    fun showPoints(list: List<Waypoint>)
}