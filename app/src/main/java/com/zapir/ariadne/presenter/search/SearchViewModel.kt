package com.zapir.ariadne.presenter.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.repositories.PointsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(
        private val interactor: PointsRepository
) : ViewModel() {

    var from = MutableLiveData<Waypoint>()
    var to = MutableLiveData<Waypoint>()
    var points = MutableLiveData<List<Waypoint>>()

    fun getPoints() =
            interactor.getPoints()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                points.postValue(it)
                            },
                            {
                                print(it.message)

                            }
                    )

    fun chooseFromPoint(Waypoint: Waypoint) {
        from.postValue(Waypoint)
    }

    fun chooseToPoint(Waypoint: Waypoint) {
        to.postValue(Waypoint)
    }
}