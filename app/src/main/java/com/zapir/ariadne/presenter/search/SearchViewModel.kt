package com.zapir.ariadne.presenter.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.repositories.PointsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(
        private val interactor: PointsRepository
) : ViewModel() {

    var from = MutableLiveData<Waypoint>()
    var to = MutableLiveData<Waypoint>()
    var state = MutableLiveData<WaypointsState>()
    var points = MutableLiveData<List<Waypoint>>()

    fun getPoints() =
            interactor.getPoints()
                    .doOnSubscribe {
                        state.postValue(WaypointsState.LoadingState(true))
                    }
                    .doAfterTerminate {
                        state.postValue(WaypointsState.LoadingState(false))
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                state.postValue(WaypointsState.SuccessState(it))
                            },
                            {
                                state.postValue(WaypointsState.FailState(it))

                            }
                    )

    fun chooseFromPoint(Waypoint: Waypoint) {
        from.postValue(Waypoint)
    }

    fun chooseToPoint(Waypoint: Waypoint) {
        to.postValue(Waypoint)
    }
}