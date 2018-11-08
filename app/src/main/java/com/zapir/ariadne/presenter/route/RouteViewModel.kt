package com.zapir.ariadne.presenter.route

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.interactors.RouteInteractor


class RouteViewModel(
        private val interactor: RouteInteractor
): ViewModel() {

    val route = MutableLiveData<List<Point>>()// паттерн обсервер. Когда постим сюда новые
    // данные, они рассылаются по подписчикам

    fun createRoute(from: Point, to: Point) {
        val result = interactor.createRoute(from, to)
        route.postValue(result)
    }


}