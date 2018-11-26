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
                        Log.e("dlf", "BEGIN")
                        state.postValue(WaypointsState.LoadingState(true))
                    }
                    .doFinally {
                        state.postValue(WaypointsState.LoadingState(false))
                        Log.e("dlf", "TERMINATE")
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                state.postValue(WaypointsState.SuccessState(it))
                                //points.postValue(it)
                            },
                            {
                                print(it.message)
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