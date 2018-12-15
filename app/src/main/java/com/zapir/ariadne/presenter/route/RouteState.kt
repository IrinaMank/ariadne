package com.zapir.ariadne.presenter.route

import com.zapir.ariadne.model.entity.Waypoint

sealed class RouteState {
    class LoadingState(val loading: Boolean) : RouteState()
    class SuccessState(val list: List<Waypoint>, val image: String) : RouteState()
    class FailState(val error: Throwable) : RouteState()
}