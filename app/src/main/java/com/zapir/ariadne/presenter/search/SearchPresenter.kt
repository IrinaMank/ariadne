package com.zapir.ariadne.presenter.search

import com.arellomobile.mvp.InjectViewState
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.points.PointsRepository
import com.zapir.ariadne.presenter.BasePresenter

@InjectViewState
class SearchPresenter : BasePresenter<SearchView>() {
    private val pointsInteractor = PointsRepository()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

//        pointsInteractor.getPoints()
//                .subscribe(
//                {
//                    viewState.showPoints(it)
//                },
//                {
//
//                }
//        ).connect()
    }

    fun search(text: CharSequence) {

    }

    fun choosePoint(waypoint: Waypoint) {

    }
}
