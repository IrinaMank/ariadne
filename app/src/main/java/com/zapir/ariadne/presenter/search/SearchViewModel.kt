package com.zapir.ariadne.presenter.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.repositories.PointsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(
        private val interactor: PointsRepository
) : ViewModel() {

    var from = MutableLiveData<Point>()
    var to = MutableLiveData<Point>()
    var points = MutableLiveData<List<Point>>()

    fun getPoints() =
            interactor.getPoints()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                points.postValue(it)
                            },
                            {

                            }
                    )

    fun chooseFromPoint(point: Point) {
        from.postValue(point)
    }

    fun chooseToPoint(point: Point) {
        to.postValue(point)
    }
}