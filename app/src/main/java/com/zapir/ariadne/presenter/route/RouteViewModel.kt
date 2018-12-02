package com.zapir.ariadne.presenter.route

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.interactors.RouteInteractor
import com.zapir.ariadne.model.repositories.PointsRepository
import com.zapir.ariadne.presenter.search.WaypointsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RouteViewModel(
        private val interactor: PointsRepository
): ViewModel() {

    val route = MutableLiveData<List<Waypoint>>()// паттерн обсервер. Когда постим сюда новые
    // данные, они рассылаются по подписчикам
    var state = MutableLiveData<WaypointsState>()

    fun createRoute(from: Waypoint, to: Waypoint) {
        val result = interactor.getPoints()
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
    }


}