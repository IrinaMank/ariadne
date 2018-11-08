package com.zapir.ariadne.presenter.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.points.PointsRepository

class SearchViewModel : ViewModel() {
    private val pointsInteractor = PointsRepository()

    var from = MutableLiveData<Point>()
    var to = MutableLiveData<Point>()
    var points = MutableLiveData<List<Point>>()

    fun getPoints() =
            pointsInteractor.getPoints()
                    .subscribe(
                            {
                                points.postValue(it)
                            },
                            {

                            }
                    )

    fun choosePoint(point: Point) {
        from.postValue(point)
    }
}