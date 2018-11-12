package com.zapir.ariadne.presenter.route

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.interactors.RouteInteractor


class RouteViewModel(
        private val interactor: RouteInteractor
): ViewModel() {

    val route = MutableLiveData<List<Waypoint>>()// паттерн обсервер. Когда постим сюда новые
    // данные, они рассылаются по подписчикам

    fun createRoute(from: Waypoint, to: Waypoint) {
        val result = interactor.createRoute(from, to)
        route.postValue(result)
    }


}